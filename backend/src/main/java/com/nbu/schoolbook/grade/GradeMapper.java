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
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setId(gradeEntity.getId());
        gradeDTO.setGrade(gradeEntity.getGrade());
        gradeDTO.setDate(gradeEntity.getDate());
        gradeDTO.setStudentId(gradeEntity.getStudent().getId());
        gradeDTO.setSubjectId(gradeEntity.getSubject().getId());
        gradeDTO.setSubjectName(gradeEntity.getSubject().getName());
        return gradeDTO;
    }

    public GradeEntity mapToEntity(GradeDTO gradeDTO) {
        return modelMapper.map(gradeDTO, GradeEntity.class);
    }

    public GradeEntity mapCreateToEntity(CreateGradeDTO createGradeDTO) {
        return modelMapper.map(createGradeDTO, GradeEntity.class);
    }
}
