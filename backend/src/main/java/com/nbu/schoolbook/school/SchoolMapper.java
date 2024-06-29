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

    public SchoolDTO mapToDTO(SchoolEntity school){
        return modelMapper.map(school, SchoolDTO.class);
    }

    public SchoolEntity mapToEntity(SchoolDTO schoolDTO){
        return modelMapper.map(schoolDTO, SchoolEntity.class);
    }

    public SchoolEntity mapCreateToEntity(CreateSchoolDTO createSchoolDTO) {
        return modelMapper.map(createSchoolDTO, SchoolEntity.class);
    }

}
