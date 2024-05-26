package com.nbu.schoolbook.role;

import com.nbu.schoolbook.role.RoleService;
import com.nbu.schoolbook.role.dto.RoleDTO;
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
public class RoleController {

    private RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO savedRole = roleService.createRole(roleDTO);
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("id") Long id,
                                              @RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

}
