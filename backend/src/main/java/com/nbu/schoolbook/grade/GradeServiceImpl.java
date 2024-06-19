package com.nbu.schoolbook.grade;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final ClassSessionRepository classSessionRepository;
    private final GradeMapper gradeMapper;


    @Override
    public GradeDTO createGrade(CreateGradeDTO createGradeDTO) {
        GradeEntity gradeEntity = new GradeEntity();
        gradeEntity.setGrade(createGradeDTO.getGrade());
        gradeEntity.setDate(LocalDate.now());

        StudentEntity student = studentRepository.findById(createGradeDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        gradeEntity.setStudent(student);

        ClassSessionEntity classSession = classSessionRepository.findById(createGradeDTO.getClassSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found"));
        gradeEntity.setClassSession(classSession);

        GradeEntity savedGrade = gradeRepository.save(gradeEntity);
        return gradeMapper.mapToDTO(savedGrade);
    }

    @Override
    public GradeDTO getGradeById(Long id) {
        GradeEntity grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));
        return gradeMapper.mapToDTO(grade);
    }

    @Override
    public List<GradeDTO> getAllGrades() {
        List<GradeEntity> grades = gradeRepository.findAll();
        return grades.stream()
                .map(gradeMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradeDTO updateGrade(Long id, UpdateGradeDTO updateGradeDTO) {
        GradeEntity gradeEntity = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));

        gradeEntity.setGrade(updateGradeDTO.getGrade());

        StudentEntity student = studentRepository.findById(updateGradeDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        gradeEntity.setStudent(student);

        ClassSessionEntity classSession = classSessionRepository.findById(updateGradeDTO.getClassSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found"));
        gradeEntity.setClassSession(classSession);

        GradeEntity updatedGrade = gradeRepository.save(gradeEntity);
        return gradeMapper.mapToDTO(updatedGrade);
    }

    @Override
    public void deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grade not found");
        }
        gradeRepository.deleteById(id);
    }
}
