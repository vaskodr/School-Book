package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
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

    @PostMapping
    public ResponseEntity<CreateSubjectDTO> createSubject(@RequestBody CreateSubjectDTO createSubjectDTO) {
        CreateSubjectDTO createdSubject = subjectService.saveSubject(createSubjectDTO);
        return ResponseEntity.ok(createdSubject);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable("id") Long id) {
        SubjectDTO subjectDTO = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subjectDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable("id") Long id, @RequestBody SubjectDTO subjectDTO) {
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subjectDTO);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject deleted successfully!");
    }


}
