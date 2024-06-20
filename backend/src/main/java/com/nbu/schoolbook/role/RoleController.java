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
@RequestMapping("/api/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<RoleDTO> createRole(@RequestBody CreateRoleDTO createRoleDTO) {
        RoleDTO savedRole = roleService.createRole(createRoleDTO);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") Long id) {
        RoleDTO roleDTO = roleService.getRoleById(id);
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roleDTOs = roleService.getRoles();
        return ResponseEntity.ok(roleDTOs);
    }

    @PutMapping("{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("id") Long id,
                                              @RequestBody UpdateRoleDTO updateRoleDTO) {
        RoleDTO updatedRole = roleService.updateRole(id, updateRoleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

}
