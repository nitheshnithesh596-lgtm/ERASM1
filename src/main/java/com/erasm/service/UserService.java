package com.erasm.service;

import java.util.List;

import com.erasm.dto.ChangePasswordDTO;
import com.erasm.dto.UserDTO;

public interface UserService {

     UserDTO saveUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

     UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);
    void changePassword(Long userId, ChangePasswordDTO dto);
}