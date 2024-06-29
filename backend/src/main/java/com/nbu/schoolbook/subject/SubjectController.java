package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import com.nbu.schoolbook.subject.dto.UpdateSubjectDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<String> createSubject(@RequestBody CreateSubjectDTO createSubjectDTO) {
        subjectService.createSubject(createSubjectDTO);
        return ResponseEntity.ok("Subject with name: " + createSubjectDTO.getName() + " created successfully!");
    }
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long subjectId) {
        SubjectDTO subjectDTO = subjectService.getSubjectById(subjectId);
        return ResponseEntity.ok(subjectDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<String> updateSubject(@PathVariable Long subjectId, @RequestBody UpdateSubjectDTO updateSubjectDTO) {
        subjectService.updateSubject(subjectId, updateSubjectDTO);
        return ResponseEntity.ok("Subject with id: " + subjectId + " successfully updated!");
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok("Subject deleted successfully!");
    }


}
