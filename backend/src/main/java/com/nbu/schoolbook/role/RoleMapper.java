package com.nbu.schoolbook.role;

import com.nbu.schoolbook.role.dto.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        RoleMapper.modelMapper = modelMapper;
    }

    public RoleDTO mapToDTO(RoleEntity role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public RoleEntity mapToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }

//    public static RoleEntity mapToEntity(RoleDTO roleDTO) {
//        return new RoleEntity(
//                roleDTO.id(),
//                roleDTO.name()
//                r
//        );
//    }
//
//    public static RoleDTO mapToDTO(RoleEntity role) {
//        return new RoleDTO(
//                role.getId(),
//                role.getName()
//        );
//    }

}
