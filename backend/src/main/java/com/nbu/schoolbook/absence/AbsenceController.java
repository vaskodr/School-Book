package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    @PostMapping
    public ResponseEntity<AbsenceDTO> createAbsence(@RequestBody CreateAbsenceDTO createAbsenceDTO) {
        AbsenceDTO absenceDTO = absenceService.createAbsence(createAbsenceDTO);
        return new ResponseEntity<>(absenceDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> getAbsenceById(@PathVariable Long id) {
        AbsenceDTO absenceDTO = absenceService.getAbsenceById(id);
        return new ResponseEntity<>(absenceDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AbsenceDTO>> getAllAbsences() {
        List<AbsenceDTO> absences = absenceService.getAllAbsences();
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceDTO> updateAbsence(@PathVariable Long id, @RequestBody UpdateAbsenceDTO updateAbsenceDTO) {
        AbsenceDTO absenceDTO = absenceService.updateAbsence(id, updateAbsenceDTO);
        return new ResponseEntity<>(absenceDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
