package com.nbu.schoolbook.grade;

import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbu.schoolbook.grade.dto.CreateGradeDTO;

@Component
public class GradeMapper {

    @Autowired
    private ModelMapper modelMapper;

    public GradeDTO mapToDTO(GradeEntity gradeEntity) {
        return modelMapper.map(gradeEntity, GradeDTO.class);
    }

    public GradeEntity mapToEntity(GradeDTO gradeDTO) {
        return modelMapper.map(gradeDTO, GradeEntity.class);
    }

    public GradeEntity mapCreateToEntity(CreateGradeDTO createGradeDTO) {
        return modelMapper.map(createGradeDTO, GradeEntity.class);
    }
}
