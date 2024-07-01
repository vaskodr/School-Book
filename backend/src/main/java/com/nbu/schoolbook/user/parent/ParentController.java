package com.nbu.schoolbook.user.parent;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nbu.schoolbook.user.student.dto.StudentDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/parent")
public class ParentController {
    private final ParentService parentService;

    @GetMapping("/{userId}/children")
    public ResponseEntity<List<StudentDTO>> getParentChildren(@PathVariable String userId) {
        List<StudentDTO> studentDTOS = parentService.getParentChildrenByUserId(userId);
        return ResponseEntity.ok(studentDTOS);
    }

    @PostMapping("/{parentId}/student/{studentId}")
    public ResponseEntity<String> unassignParent(@PathVariable Long parentId,
                                                 @PathVariable Long studentId) {
        parentService.unassignParent(parentId, studentId);
        return ResponseEntity.ok("Parent unassigned!");
    }

}
