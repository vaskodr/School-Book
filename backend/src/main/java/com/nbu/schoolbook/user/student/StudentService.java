package com.nbu.schoolbook.user.student;

import java.util.List;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.dto.StudentClassDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import com.nbu.schoolbook.user.student.dto.UpdateStudentDTO;

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

    StudentClassDTO getStudentClassByUserId(String userId);
    StudentDTO getStudentByUserID(String userId);

}
