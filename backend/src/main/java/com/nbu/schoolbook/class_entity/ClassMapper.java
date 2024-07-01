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
        ClassDTO classDTO = new ClassDTO();
        classDTO.setId(classEntity.getId());
        classDTO.setName(classEntity.getName());
        classDTO.setLevel(classEntity.getLevel());
        classDTO.setMentorId(classEntity.getMentor().getId());

        classDTO.setSchoolId(classEntity.getSchool().getId());
        classDTO.setSchoolName(classEntity.getSchool().getName());
        return classDTO;
    }

    public ClassEntity mapToEntity(ClassDTO classDTO) {
        return modelMapper.map(classDTO, ClassEntity.class);
    }
}
