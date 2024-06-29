package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.student.dto.*;

import java.util.List;

public interface StudentService {
    StudentDTO registerStudent(RegisterDTO registerDTO, Long classId);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    StudentDTO updateStudent(Long id, UpdateStudentDTO updateStudentDTO);
    void deleteStudent(Long id);



    void enrollStudent(Long studentId, Long classId);
    void unenrollStudent(Long studentId, Long classId);

    StudentClassDTO getStudentByUserId(String userId);


}
