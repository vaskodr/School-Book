package com.nbu.schoolbook.grade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getGradesByStudentId(@PathVariable Long studentId) {
        List<GradeDTO> grades = gradeService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @PostMapping
    public ResponseEntity<GradeDTO> createGrade(@RequestBody CreateGradeDTO createGradeDTO) {
        GradeDTO gradeDTO = gradeService.createGrade(createGradeDTO);
        return ResponseEntity.status(201).body(gradeDTO);
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable Long gradeId, @RequestBody UpdateGradeDTO updateGradeDTO) {
        GradeDTO gradeDTO = gradeService.updateGrade(gradeId, updateGradeDTO);
        return ResponseEntity.ok(gradeDTO);
    }
}
