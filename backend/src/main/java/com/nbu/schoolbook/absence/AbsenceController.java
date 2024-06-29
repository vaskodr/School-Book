package com.nbu.schoolbook.absence;

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

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByStudentId(@PathVariable Long studentId) {
        List<AbsenceDTO> absences = absenceService.getAbsencesByStudentId(studentId);
        return ResponseEntity.ok(absences);
    }

    @PostMapping
    public ResponseEntity<AbsenceDTO> createAbsence(@RequestBody CreateAbsenceDTO createAbsenceDTO) {
        AbsenceDTO absenceDTO = absenceService.createAbsence(createAbsenceDTO);
        return ResponseEntity.status(201).body(absenceDTO);
    }

    @PutMapping("/{absenceId}")
    public ResponseEntity<AbsenceDTO> updateAbsence(@PathVariable Long absenceId, @RequestBody UpdateAbsenceDTO updateAbsenceDTO) {
        AbsenceDTO absenceDTO = absenceService.updateAbsence(absenceId, updateAbsenceDTO);
        return ResponseEntity.ok(absenceDTO);
    }
}
