package com.nbu.schoolbook.role;

import javax.management.relation.Role;
import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO role);
    RoleDTO getRoleById(Long id);
    List<RoleDTO> getRoles();
    RoleDTO updateRole(Long id, RoleDTO role);
    void deleteRole(Long id);
}
