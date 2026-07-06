package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.erasm.dto.LoginRequest;
import com.erasm.dto.LoginResponse;
import com.erasm.dto.RegisterRequest;
import com.erasm.dto.RegisterResponse;
import com.erasm.entity.Role;
import com.erasm.entity.User;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.RoleRepository;
import com.erasm.repository.UserRepository;
import com.erasm.security.JwtService;
import com.erasm.serviceimpl.AuthServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterSuccess() {

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        RegisterRequest request = new RegisterRequest();
        request.setUsername("nithesh");
        request.setEmail("nithesh@gmail.com");
        request.setPassword("password123");
        request.setRoleId(1L);

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(roleRepository.findById(1L))
                .thenReturn(Optional.of(role));

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RegisterResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("User registered successfully", response.getMessage());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterDuplicateEmail() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("nithesh@gmail.com");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(request));

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterRoleNotFound() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("nithesh");
        request.setEmail("nithesh@gmail.com");
        request.setPassword("password123");
        request.setRoleId(1L);

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(roleRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.register(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        User user = new User();
        user.setUsername("nithesh");
        user.setEmail("nithesh@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole(role);

        LoginRequest request = new LoginRequest();
        request.setUsername("nithesh");
        request.setPassword("password123");

        when(userRepository.findByUsername("nithesh"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password123", "encodedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken("nithesh", "ADMIN"))
                .thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("ADMIN", response.getRole());
        assertEquals("nithesh@gmail.com", response.getEmail());

        verify(jwtService).generateToken("nithesh", "ADMIN");
    }

    @Test
    void testLoginUserNotFound() {

        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("password");

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.login(request));
    }

    @Test
    void testLoginInvalidPassword() {

        Role role = new Role();
        role.setRoleName("ADMIN");

        User user = new User();
        user.setUsername("nithesh");
        user.setPassword("encodedPassword");
        user.setRole(role);

        LoginRequest request = new LoginRequest();
        request.setUsername("nithesh");
        request.setPassword("wrongPassword");

        when(userRepository.findByUsername("nithesh"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request));

        assertEquals("Invalid credentials", exception.getMessage());

        verify(jwtService, never()).generateToken(any(), any());
    }
}