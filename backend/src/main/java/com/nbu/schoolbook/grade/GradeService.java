package com.nbu.schoolbook.grade;

import java.util.List;

import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;

public interface GradeService {
    List<GradeDTO> getGradesByStudentId(Long studentId);
    GradeDTO createGrade(CreateGradeDTO createGradeDTO);
    GradeDTO updateGrade(Long gradeId, UpdateGradeDTO updateGradeDTO);
}
