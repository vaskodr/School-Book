package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.student.dto.StudentClassDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @GetMapping("/{userId}")
    public StudentClassDTO getStudentByUserId(@PathVariable String userId) {
       return studentService.getStudentByUserId(userId);
    }

    @PostMapping("/{studentId}/enroll")
    public ResponseEntity<String> enrollStudent(@PathVariable Long studentId,
                                                @RequestParam Long classId) {
        studentService.enrollStudent(classId, studentId);
        return ResponseEntity.ok("Student enrolled successfully");
    }

    @PostMapping("/{studentId}/unenroll")
    public ResponseEntity<String> unenrollStudent(@PathVariable Long studentId,
                                                  @RequestParam Long classId) {
        studentService.unenrollStudent(classId, studentId);
        return ResponseEntity.ok("Student unenrolled successfully");
    }
}
