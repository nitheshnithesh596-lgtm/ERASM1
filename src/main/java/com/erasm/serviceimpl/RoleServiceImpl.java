package com.erasm.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erasm.dto.RoleDTO;
import com.erasm.entity.Role;
import com.erasm.exception.ResourceNotFoundException;
import com.erasm.mapper.RoleMapper;
import com.erasm.repository.RoleRepository;
import com.erasm.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {

        if (roleRepository.findByRoleName(roleDTO.getRoleName()).isPresent()) {
            throw new RuntimeException("Role already exists.");
        }

        Role role = RoleMapper.toEntity(roleDTO);

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toDTO(savedRole);
    }

    @Override
    public List<RoleDTO> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID : " + roleId));

        return RoleMapper.toDTO(role);
    }

    @Override
    public RoleDTO updateRole(Long roleId, RoleDTO roleDTO) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID : " + roleId));

        role.setRoleName(roleDTO.getRoleName());

        Role updatedRole = roleRepository.save(role);

        return RoleMapper.toDTO(updatedRole);
    }

    @Override
    public void deleteRole(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID : " + roleId));

        roleRepository.delete(role);
    }

}