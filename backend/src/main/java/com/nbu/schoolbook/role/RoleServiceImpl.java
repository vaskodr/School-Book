package com.nbu.schoolbook.role;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        RoleEntity role = roleMapper.mapToEntity(roleDTO);
        role = roleRepository.save(role);
        return roleMapper.mapToDTO(role);
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Role does not exist with id: " + id
                        )
                );
        return roleMapper.mapToDTO(role);
    }

    @Override
    public List<RoleDTO> getRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Role does not exist with id: " + id
                        )
                );

        role.setName(roleDTO.getName());

        role = roleRepository.save(role);
        return roleMapper.mapToDTO(role);
    }

    @Override
    public void deleteRole(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Role does not exist with id: " + id
                        )
                );
        roleRepository.deleteById(id);
    }
}