package com.erasm.mapper;

import com.erasm.dto.UserDTO;
import com.erasm.entity.User;

public class UserMapper {

  
    public static UserDTO toDTO(User user) {

        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());

        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getRoleId());
        }

        return dto;
    }

    
    public static User toEntity(UserDTO dto) {

        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

}