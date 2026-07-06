package com.erasm.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.erasm.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            logger.warn("Invalid registration request: duplicate email.");

            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> {
                    logger.error("Role not found during user registration.");
                    return new ResourceNotFoundException("Role not found");
                });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        logger.info("User registration completed successfully.");

        RegisterResponse response = new RegisterResponse();
        response.setMessage("User registered successfully");

        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Invalid login request.");
                    return new ResourceNotFoundException("User not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            logger.warn("Invalid login request.");

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().getRoleName()
        );

        logger.info("User login successful.");

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getRoleName());

        return response;
    }
}