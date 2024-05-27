package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@AllArgsConstructor
public class SchoolMapper {

    private final ModelMapper modelMapper;
    private final ClassRepository classRepository;
    public SchoolDTO mapToDTO(SchoolEntity school) {
        SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);
        schoolDTO.setClassIds(school.getClasses().stream().map(ClassEntity::getId).collect(Collectors.toList()));
        return schoolDTO;
    }

    public SchoolEntity mapDTOToEntity(SchoolDTO schoolDTO) {
        SchoolEntity schoolEntity = modelMapper.map(schoolDTO, SchoolEntity.class);
        Set<ClassEntity> classes = StreamSupport.stream(classRepository.findAllById(schoolDTO.getClassIds()).spliterator(), false)
                .collect(Collectors.toSet());
        schoolEntity.setClasses(classes);
        return schoolEntity;
    }

    public CreateSchoolDTO mapEntityToCreateDTO(SchoolEntity school) {
        return modelMapper.map(school, CreateSchoolDTO.class);
    }

    public SchoolEntity mapCreateDTOToEntity(CreateSchoolDTO createSchoolDTO) {
        return modelMapper.map(createSchoolDTO, SchoolEntity.class);
    }
}
