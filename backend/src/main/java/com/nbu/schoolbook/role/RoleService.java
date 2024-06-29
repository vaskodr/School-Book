package com.nbu.schoolbook.role;

import com.nbu.schoolbook.role.dto.CreateRoleDTO;
import com.nbu.schoolbook.role.dto.RoleDTO;
import com.nbu.schoolbook.role.dto.UpdateRoleDTO;

import java.util.List;

public interface RoleService {
    void createRole(CreateRoleDTO createRoleDTO);
    RoleDTO getRoleById(Long id);
    List<RoleDTO> getRoles();
    void updateRole(Long id, UpdateRoleDTO updateRoleDTO);
    void deleteRole(Long id);



}
