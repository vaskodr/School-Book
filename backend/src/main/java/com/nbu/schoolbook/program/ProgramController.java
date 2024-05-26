package com.nbu.schoolbook.program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<ProgramEntity> createProgram(@RequestBody ProgramEntity program) {
        ProgramEntity createdProgram = programService.saveProgram(program);
        return ResponseEntity.ok(createdProgram);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramEntity> updateProgram(@PathVariable long id, @RequestBody ProgramEntity program) {
        ProgramEntity updatedProgram = programService.updateProgram(id, program);
        return ResponseEntity.ok(updatedProgram);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramEntity> getProgramById(@PathVariable long id) {
        ProgramEntity program = programService.getProgramById(id);
        return ResponseEntity.ok(program);
    }

    @GetMapping
    public ResponseEntity<List<ProgramEntity>> getAllPrograms() {
        List<ProgramEntity> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }
}
