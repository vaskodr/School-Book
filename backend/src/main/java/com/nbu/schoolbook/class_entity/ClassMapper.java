package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ClassMapper {
    private final ModelMapper modelMapper;

    public ClassDTO mapToDTO(ClassEntity classEntity) {
        return modelMapper.map(classEntity, ClassDTO.class);
    }

    public ClassEntity mapToEntity(ClassDTO classDTO) {
        return modelMapper.map(classDTO, ClassEntity.class);
    }
}
