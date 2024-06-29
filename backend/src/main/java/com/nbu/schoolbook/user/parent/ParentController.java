package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.user.student.dto.StudentDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/parent")
public class ParentController {
    private final ParentService parentService;

    @GetMapping("/{parentId}/children")
    public ResponseEntity<List<StudentDTO>> getParentChildren(@PathVariable Long parentId) {
        List<StudentDTO> studentDTOS = parentService.getParentChildren(parentId);
        return ResponseEntity.ok(studentDTOS);
    }
}
