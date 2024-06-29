package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.ProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/programs")
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;



    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDTO> getProgramById(@PathVariable Long programId) {
        ProgramDTO programDTO = programService.getProgramById(programId);
        return ResponseEntity.ok(programDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllProgramsBySchoolId(@PathVariable Long schoolId) {
        List<ProgramDTO> programs = programService.getAllProgramsBySchoolId(schoolId);
        return ResponseEntity.ok(programs);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramDTO> updateProgram(@PathVariable Long programId, @RequestBody UpdateProgramDTO updateProgramDTO) {
        ProgramDTO programDTO = programService.updateProgram(programId, updateProgramDTO);
        return ResponseEntity.ok(programDTO);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        programService.deleteProgram(programId);
        return ResponseEntity.noContent().build();
    }
}
