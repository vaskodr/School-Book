package com.nbu.schoolbook.grade;


import com.nbu.schoolbook.grade.dto.GradeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class GradeMapper {
    private final ModelMapper modelMapper;

    @Autowired

    public GradeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GradeDTO mapToDTO(GradeEntity grade) {
        return modelMapper.map(grade, GradeDTO.class);
    }

    public GradeEntity mapToEntity(GradeDTO gradeDTO) {
        return modelMapper.map(gradeDTO, GradeEntity.class);
    }
}
