package com.nbu.schoolbook.grade;

import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;

import java.util.List;

public interface GradeService {
    GradeDTO createGrade(CreateGradeDTO createGradeDTO);
    GradeDTO getGradeById(Long id);
    List<GradeDTO> getAllGrades();
    GradeDTO updateGrade(Long id, UpdateGradeDTO updateGradeDTO);
    void deleteGrade(Long id);

}
