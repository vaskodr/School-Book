package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable("id") Long id) {
        TeacherDTO teacherDTO = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teacherDTOs = teacherService.getAllTeachers();
        return ResponseEntity.ok(teacherDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id,
                                                    @RequestBody UpdateTeacherDTO updateTeacherDTO) {
        TeacherDTO updatedTeacher = teacherService.updateTeacher(id, updateTeacherDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher has been deleted successfully!");
    }
}
