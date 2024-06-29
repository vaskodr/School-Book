package com.nbu.schoolbook.grade;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.teacher.TeacherRepository;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public List<GradeDTO> getGradesByStudentId(Long studentId) {
        List<GradeEntity> grades = gradeRepository.findAllByStudentId(studentId);
        return grades.stream().map(gradeMapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public GradeDTO createGrade(CreateGradeDTO createGradeDTO) {
        GradeEntity gradeEntity = gradeMapper.mapCreateToEntity(createGradeDTO);
        gradeEntity.setDate(LocalDate.now());
        gradeEntity.setStudent(studentRepository.findById(createGradeDTO.getStudentId()).orElseThrow());
        gradeEntity.setTeacher(teacherRepository.findById(createGradeDTO.getTeacherId()).orElseThrow());
        gradeEntity.setSubject(subjectRepository.findById(createGradeDTO.getSubjectId()).orElseThrow());

        GradeEntity savedGrade = gradeRepository.save(gradeEntity);
        return gradeMapper.mapToDTO(savedGrade);
    }

    @Override
    public GradeDTO updateGrade(Long gradeId, UpdateGradeDTO updateGradeDTO) {
        GradeEntity gradeEntity = gradeRepository.findById(gradeId).orElseThrow();
        gradeEntity.setGrade(updateGradeDTO.getGrade());
        gradeEntity.setStudent(studentRepository.findById(updateGradeDTO.getStudentId()).orElseThrow());
        gradeEntity.setSubject(subjectRepository.findById(updateGradeDTO.getSubjectId()).orElseThrow());

        GradeEntity updatedGrade = gradeRepository.save(gradeEntity);
        return gradeMapper.mapToDTO(updatedGrade);
    }
}
