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
@RequestMapping("/api/school/{schoolId}/teacher")
public class TeacherController {
    private final TeacherService teacherService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/teacher/add-teacher")
    public ResponseEntity<TeacherDTO> addTeacher(@PathVariable Long schoolId, @RequestBody RegisterDTO createTeacherDTO){
        TeacherDTO teacherDTO = teacherService.registerTeacher(createTeacherDTO, schoolId);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long schoolId,
                                                     @PathVariable Long teacherId) {
        TeacherDTO teacherDTO = teacherService.getTeacherById(schoolId, teacherId);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(@PathVariable Long schoolId) {
        List<TeacherDTO> teacherDTOs = teacherService.getAllTeachersBySchoolId(schoolId);
        return ResponseEntity.ok(teacherDTOs);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<String> updateTeacher(@PathVariable Long schoolId,
                                                @PathVariable Long teacherId,
                                                    @RequestBody UpdateTeacherDTO updateTeacherDTO) {
        teacherService.updateTeacher(schoolId, teacherId, updateTeacherDTO);
        return ResponseEntity.ok(
                "Teacher in school id: "
                        + schoolId
                        + " with id: "
                        + teacherId
                        + " updated successfully!"
        );
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long schoolId,
                                                @PathVariable Long teacherId) {
        teacherService.deleteTeacher(schoolId, teacherId);
        return ResponseEntity.ok("Teacher has been deleted successfully!");
    }


}
