package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.absence.AbsenceMapper;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassMapper;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.grade.GradeMapper;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.parent.ParentMapper;
import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ClassMapper classMapper;
    private final GradeMapper gradeMapper;
    private final AbsenceMapper absenceMapper;
    private final ParentMapper parentMapper;

    public StudentDTO mapToDTO(StudentEntity student) {
        UserEntity user = userRepository.findById(student.getUserEntity().getId()).orElseThrow(
                () -> new RuntimeException("User not found!")
        );
        ClassEntity classEntity = classRepository.findById(student.getStudentClass().getId()).orElseThrow(
                () -> new RuntimeException("Class not found!")
        );
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(user.getFirstName());
        studentDTO.setLastName(user.getLastName());
        studentDTO.setClassDTO(classMapper.mapToDTO(classEntity));
        return studentDTO;
    }

    public StudentDetailsDTO mapToDetailsDTO(StudentEntity student) {
        UserEntity user = student.getUserEntity();
        StudentDetailsDTO studentDetailsDTO = new StudentDetailsDTO();
        studentDetailsDTO.setFirstName(user.getFirstName());
        studentDetailsDTO.setLastName(user.getLastName());
        studentDetailsDTO.setDateOfBirth(user.getDateOfBirth());
        studentDetailsDTO.setGender(user.getGender());
        studentDetailsDTO.setPhone(user.getPhone());
        studentDetailsDTO.setEmail(user.getEmail());
        studentDetailsDTO.setUsername(user.getUsername());

        if (student.getGrades() != null) {
            studentDetailsDTO.setGrades(
                    student.getGrades().stream()
                            .map(gradeMapper::mapToDTO)
                            .collect(Collectors.toList())
            );
        } else {
            studentDetailsDTO.setGrades(null);
        }

        if (student.getAbsences() != null) {
            studentDetailsDTO.setAbsences(
                    student.getAbsences().stream()
                            .map(absenceMapper::mapToDTO)
                            .collect(Collectors.toList())
            );
        } else {
            studentDetailsDTO.setAbsences(null);
        }

        // StudentDetailsDTO no longer maps parents directly here

        studentDetailsDTO.setSchoolName(student.getStudentClass().getSchool().getName());
        studentDetailsDTO.setClassDTO(classMapper.mapToDTO(student.getStudentClass()));

        return studentDetailsDTO;
    }
}