package com.nbu.schoolbook.grade;

import java.util.List;

public interface GradeService {
    GradeEntity saveGrade(GradeEntity grade);
    GradeEntity updateGrade(long id, GradeEntity grade);
    void deleteGrade(long id);
    GradeEntity getGradeById(long id);
    List<GradeEntity> getAllGrades();
}
