package com.erasm.serviceimpl;
import com.erasm.service.AuditService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erasm.dto.ChangePasswordDTO;
import com.erasm.dto.UserDTO;
import com.erasm.entity.Role;
import com.erasm.entity.User;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.UserMapper;
import com.erasm.repository.RoleRepository;
import com.erasm.repository.UserRepository;
import com.erasm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	 private static final Logger logger =
	            LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuditService auditService;
    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        User user = UserMapper.toEntity(userDTO);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role not found with ID : " + userDTO.getRoleId()));

        user.setRole(role);

        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }
    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID : " + userId));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID : " + userId));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
       
        if (userDTO.getRoleId() != null) {

            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Role not found with ID : " + userDTO.getRoleId()));

            user.setRole(role);
        }

        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID : " + userId));

        userRepository.delete(user);
    }
    @Override
    public void changePassword(Long userId, ChangePasswordDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID : " + userId));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {

            throw new RuntimeException("Old password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);

        logger.info("Password changed successfully.");

        auditService.logAction(
                "CHANGE_PASSWORD",
                user.getUsername(),
                "Password changed");
    }
}