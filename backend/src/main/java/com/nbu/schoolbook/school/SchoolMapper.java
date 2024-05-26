package com.nbu.schoolbook.school;

import com.nbu.schoolbook.school.dto.SchoolDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SchoolMapper {

    private final ModelMapper modelMapper;
    public SchoolEntity mapToEntity(SchoolDTO schoolDTO) {
        return modelMapper.map(schoolDTO, SchoolEntity.class);
    }

    public SchoolDTO mapToDTO(SchoolEntity school) {
        return modelMapper.map(school, SchoolDTO.class);
    }
}
