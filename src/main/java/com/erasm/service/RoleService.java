package com.erasm.service;

import java.util.List;

import com.erasm.dto.RoleDTO;

public interface RoleService {
	
	
	
     RoleDTO saveRole(RoleDTO roleDTO);
     
     List<RoleDTO> getAllRoles();
     
     RoleDTO getRoleById(Long roleId);
     
     
     RoleDTO updateRole(Long roleId,RoleDTO roleDTO);
     
     void deleteRole(Long roleId);
     
     
     
     

}
