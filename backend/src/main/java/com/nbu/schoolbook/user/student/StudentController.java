package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.dto.StudentClassDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import com.nbu.schoolbook.user.student.dto.UpdateStudentDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/api/school/{schoolId}/classes/{classId}/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@PathVariable Long schoolId,
                                                 @PathVariable Long classId,
                                                 @RequestBody RegisterDTO createStudentDTO){
        studentService.registerStudent(createStudentDTO, schoolId, classId);
        return ResponseEntity.ok(
                "Student in school id: "
                + schoolId
                + " in class id: "
                + classId
                + " created and added successfully!"
        );

    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDetailsDTO> getStudentByUserId(@PathVariable Long schoolId, @PathVariable Long classId, @PathVariable Long studentId) {
        StudentDetailsDTO studentDetailsDTO = studentService.getStudentById(schoolId, classId, studentId);
        return ResponseEntity.ok(studentDetailsDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@PathVariable Long schoolId,
                                                           @PathVariable Long classId) {
        List<StudentDTO> studentDTOS = studentService.getAllStudents(schoolId, classId);
        return ResponseEntity.ok(studentDTOS);
    }

    @GetMapping("/{studentId}/details")
    public ResponseEntity<StudentDetailsDTO> getStudentDetails(@PathVariable Long studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(
                () -> new RuntimeException("Student not found!")
        );
        StudentDetailsDTO studentDetailsDTO = studentService.getStudentDetails(student);
        return ResponseEntity.ok(studentDetailsDTO);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable Long schoolId,
                                                @PathVariable Long classId,
                                                @PathVariable Long studentId,
                                                @RequestBody UpdateStudentDTO updateStudentDTO) {
        studentService.updateStudent(schoolId, classId, studentId, updateStudentDTO);
        return ResponseEntity.ok("Student with id: "
                + studentId
                + "in school id:"
                + schoolId
                + " in class id: "
                + classId
                + " update successfully!");
    }

    @DeleteMapping("/{studentId}")



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
