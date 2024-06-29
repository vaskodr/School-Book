package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/school/{schoolId}")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/teacher/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable("id") Long id) {
        TeacherDTO teacherDTO = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("teacher/list")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teacherDTOs = teacherService.getAllTeachers();
        return ResponseEntity.ok(teacherDTOs);
    }

    @PutMapping("/teacher/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id,
                                                    @RequestBody UpdateTeacherDTO updateTeacherDTO) {
        TeacherDTO updatedTeacher = teacherService.updateTeacher(id, updateTeacherDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("teacher/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher has been deleted successfully!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/teacher/add-teacher")
    public ResponseEntity<TeacherDTO> addTeacher(@PathVariable Long schoolId, @RequestBody RegisterDTO createTeacherDTO){
        TeacherDTO teacherDTO = teacherService.registerTeacher(createTeacherDTO, schoolId);
        return ResponseEntity.ok(teacherDTO);
    }
}
