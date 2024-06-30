package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class DetailsController {
    private final StudentService studentService;

    @GetMapping("/{studentId}/details")
    public ResponseEntity<StudentDTO> getStudentDetails(@PathVariable Long studentId) {
        StudentDTO studentDTO = studentService.getStudentDetailsById(studentId);
        return ResponseEntity.ok(studentDTO);
    }
}
