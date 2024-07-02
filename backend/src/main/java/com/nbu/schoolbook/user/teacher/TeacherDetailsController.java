package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
public class TeacherDetailsController {

    private TeacherService teacherService;

    @GetMapping("/{userId}")
    public ResponseEntity<TeacherDTO> getStudentByUserID(@PathVariable String userId) {
        TeacherDTO teacherDTO = teacherService.getStudentByUserID(userId);
        return ResponseEntity.ok(teacherDTO);
    }
}
