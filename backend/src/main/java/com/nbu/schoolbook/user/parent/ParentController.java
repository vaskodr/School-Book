package com.nbu.schoolbook.user.parent;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
