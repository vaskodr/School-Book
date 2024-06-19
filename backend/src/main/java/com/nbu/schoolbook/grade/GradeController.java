package com.nbu.schoolbook.grade;

import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<GradeDTO> createGrade(@RequestBody CreateGradeDTO createGradeDTO) {
        GradeDTO gradeDTO = gradeService.createGrade(createGradeDTO);
        return new ResponseEntity<>(gradeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable Long id) {
        GradeDTO gradeDTO = gradeService.getGradeById(id);
        return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable Long id, @RequestBody UpdateGradeDTO updateGradeDTO) {
        GradeDTO gradeDTO = gradeService.updateGrade(id, updateGradeDTO);
        return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GradeDTO>> getAllGrades() {
        List<GradeDTO> grades = gradeService.getAllGrades();
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }
}
