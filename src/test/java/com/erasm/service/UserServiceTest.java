package com.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.erasm.dto.UserDTO;
import com.erasm.entity.Role;
import com.erasm.entity.User;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.repository.RoleRepository;
import com.erasm.repository.UserRepository;
import com.erasm.serviceimpl.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testSaveUser_Success() {

        UserDTO dto = new UserDTO();
        dto.setEmail("test@gmail.com");
        dto.setRoleId(1L);
        dto.setUsername("testuser");
        dto.setPassword("pass123");

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        User user = new User();
        user.setUserId(1L);
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.saveUser(dto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }


    @Test
    void testSaveUser_EmailAlreadyExists() {

        UserDTO dto = new UserDTO();
        dto.setEmail("test@gmail.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.saveUser(dto));

        assertEquals("Email already exists.", ex.getMessage());
    }


    @Test
    void testSaveUser_RoleNotFound() {

        UserDTO dto = new UserDTO();
        dto.setEmail("test@gmail.com");
        dto.setRoleId(99L);

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.saveUser(dto));
    }


    @Test
    void testGetAllUsers() {

        User user1 = new User();
        user1.setUserId(1L);

        User user2 = new User();
        user2.setUserId(2L);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }


    @Test
    void testGetUserById_Success() {

        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(1L));
    }


    @Test
    void testUpdateUser_Success() {

        User existing = new User();
        existing.setUserId(1L);
        existing.setUsername("old");
        existing.setEmail("old@gmail.com");

        UserDTO dto = new UserDTO();
        dto.setUsername("new");
        dto.setEmail("new@gmail.com");
        dto.setPassword("newpass");
        dto.setRoleId(1L);

        Role role = new Role();
        role.setRoleId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(existing);

        UserDTO result = userService.updateUser(1L, dto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }


    @Test
    void testUpdateUser_NotFound() {

        UserDTO dto = new UserDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(1L, dto));
    }


    @Test
    void testUpdateUser_RoleNotFound() {

        User existing = new User();
        existing.setUserId(1L);

        UserDTO dto = new UserDTO();
        dto.setRoleId(99L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(1L, dto));
    }


    @Test
    void testDeleteUser_Success() {

        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }


    @Test
    void testDeleteUser_NotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.deleteUser(1L));
    }
}