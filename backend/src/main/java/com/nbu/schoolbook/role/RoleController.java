package com.nbu.schoolbook.role;

import com.nbu.schoolbook.role.RoleService;
import com.nbu.schoolbook.role.dto.CreateRoleDTO;
import com.nbu.schoolbook.role.dto.RoleDTO;
import com.nbu.schoolbook.role.dto.UpdateRoleDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<String> createRole(@RequestBody CreateRoleDTO createRoleDTO) {
        roleService.createRole(createRoleDTO);
        return ResponseEntity.ok("Role with name: ROLE_" + createRoleDTO.getName() + " created successfully!");
    }

    @GetMapping("{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long roleId) {
        RoleDTO roleDTO = roleService.getRoleById(roleId);
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roleDTOs = roleService.getRoles();
        return ResponseEntity.ok(roleDTOs);
    }

    @PutMapping("{roleId}")
    public ResponseEntity<String> updateRole(@PathVariable Long roleId,
                                              @RequestBody UpdateRoleDTO updateRoleDTO) {
        roleService.updateRole(roleId, updateRoleDTO);
        return ResponseEntity.ok("Role with id: " + roleId + " updated successfully!");
    }

    @DeleteMapping("{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok("Role deleted successfully");
    }

}
