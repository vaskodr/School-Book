package com.nbu.schoolbook.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<GradeEntity> createGrade(@RequestBody GradeEntity grade) {
        GradeEntity createdGrade = gradeService.saveGrade(grade);
        return ResponseEntity.ok(createdGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeEntity> updateGrade(@PathVariable long id, @RequestBody GradeEntity grade) {
        GradeEntity updatedGrade = gradeService.updateGrade(id, grade);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeEntity> getGradeById(@PathVariable long id) {
        GradeEntity grade = gradeService.getGradeById(id);
        return ResponseEntity.ok(grade);
    }

    @GetMapping
    public ResponseEntity<List<GradeEntity>> getAllGrades() {
        List<GradeEntity> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }
}
