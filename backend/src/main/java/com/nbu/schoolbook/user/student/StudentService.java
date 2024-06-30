package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.student.dto.*;

import java.util.List;

public interface StudentService {
    void registerStudent(RegisterDTO registerDTO, Long schoolId, Long classId);
    StudentDetailsDTO getStudentById(Long schoolId, Long classId, Long studentId);
    List<StudentDTO> getAllStudents(Long schoolId, Long classId);
    StudentDetailsDTO getStudentDetails(StudentEntity student);
    StudentDTO getStudentDetailsById(Long studentId);
    void updateStudent(Long schoolId, Long classId, Long studentId, UpdateStudentDTO updateStudentDTO);
    void deleteStudent(Long schoolId, Long classId, Long studentId);



    void enrollStudent(Long studentId, Long classId);
    void unenrollStudent(Long studentId, Long classId);

    StudentClassDTO getStudentByUserId(String userId);


}
