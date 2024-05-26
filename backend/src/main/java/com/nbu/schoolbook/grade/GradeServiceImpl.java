package com.nbu.schoolbook.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public GradeEntity saveGrade(GradeEntity grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public GradeEntity updateGrade(long id, GradeEntity grade) {
        Optional<GradeEntity> existingGrade = gradeRepository.findById(id);
        if (existingGrade.isPresent()) {
            GradeEntity updatedGrade = existingGrade.get();
            updatedGrade.setGrade(grade.getGrade());
            updatedGrade.setDate(grade.getDate());
            updatedGrade.setClassSession(grade.getClassSession());
            updatedGrade.setStudent(grade.getStudent());
            return gradeRepository.save(updatedGrade);
        } else {
            throw new RuntimeException("Grade not found with id: " + id);
        }
    }

    @Override
    public void deleteGrade(long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public GradeEntity getGradeById(long id) {
        return gradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
    }

    @Override
    public List<GradeEntity> getAllGrades() {
        return gradeRepository.findAll();
    }
}
