package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.student.dto.CreateStudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.UpdateStudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(CreateStudentDTO createStudentDTO);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    StudentDTO updateStudent(UpdateStudentDTO updateStudentDTO);
    void deleteStudent(Long id);
}
