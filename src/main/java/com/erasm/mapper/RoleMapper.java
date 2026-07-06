package com.erasm.mapper;

import com.erasm.dto.RoleDTO;
import com.erasm.entity.Role;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {

        if (role == null) {
            return null;
        }

        RoleDTO dto = new RoleDTO();

        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());

        return dto;
    }

    public static Role toEntity(RoleDTO dto) {

        if (dto == null) {
            return null;
        }

        Role role = new Role();

        role.setRoleId(dto.getRoleId());
        role.setRoleName(dto.getRoleName());

        return role;
    }
}