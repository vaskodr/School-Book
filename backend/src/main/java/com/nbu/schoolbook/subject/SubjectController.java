package com.nbu.schoolbook.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectEntity> createSubject(@RequestBody SubjectEntity subject) {
        SubjectEntity createdSubject = subjectService.saveSubject(subject);
        return ResponseEntity.ok(createdSubject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectEntity> updateSubject(@PathVariable long id, @RequestBody SubjectEntity subject) {
        SubjectEntity updatedSubject = subjectService.updateSubject(id, subject);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectEntity> getSubjectById(@PathVariable long id) {
        SubjectEntity subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @GetMapping
    public ResponseEntity<List<SubjectEntity>> getAllSubjects() {
        List<SubjectEntity> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
}
