package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/school/{schoolId}/program")
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;


    @PostMapping("/classes/{classId}/create")
    public ResponseEntity<String> createProgram(
            @PathVariable Long schoolId,
            @PathVariable Long classId,
            @RequestBody CreateProgramDTO createProgramDTO) {
        programService.createProgram(schoolId, classId, createProgramDTO);
        return ResponseEntity.ok(
                "Program for class id: "
                + classId
                + " in school id:"
                + schoolId
                + " created successfully!"
        );
    }

    @GetMapping("/classes/{classId}")
    public ResponseEntity<ProgramDTO> getClassProgram(@PathVariable Long schoolId, @PathVariable Long classId) {
        ProgramDTO programDTO = programService.getClassProgram(schoolId, classId);
        return ResponseEntity.ok(programDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProgramDTO>> getAllProgramsBySchoolId(@PathVariable Long schoolId) {
        List<ProgramDTO> programs = programService.getAllProgramsBySchoolId(schoolId);
        return ResponseEntity.ok(programs);
    }


    @PutMapping("/classes/{classId}/{programId}")
    public ResponseEntity<String> updateProgram(@PathVariable Long schoolId,
                                                    @PathVariable Long classId,
                                                    @PathVariable Long programId,
                                                    @RequestBody UpdateProgramDTO updateProgramDTO) {
        programService.updateProgram(schoolId, classId, programId, updateProgramDTO);
        return ResponseEntity.ok(
                "Program with id: "
                + programId
                + " in school id: "
                + schoolId
                + " in class id: "
                + classId
                + " updated successfully"
        );
    }

    @DeleteMapping("/classes/{classId}/{programId}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long schoolId,
                                                @PathVariable Long classId,
                                                @PathVariable Long programId) {
        programService.deleteProgram(schoolId, classId, programId);
        return ResponseEntity.ok("Program deleted successfully!");
    }

    @GetMapping("/student/{studentId}/weekly-program")
    public ResponseEntity<WeeklyProgramDTO> getWeeklyProgramForStudent(@PathVariable Long schoolId, @PathVariable Long studentId) {
        WeeklyProgramDTO weeklyProgram = programService.getWeeklyProgramForStudent(schoolId, studentId);
        return ResponseEntity.ok(weeklyProgram);
    }

    @GetMapping("/teacher/{teacherId}/weekly-program")
    public ResponseEntity<WeeklyTeacherProgramDTO> getWeeklyProgramForTeacher(@PathVariable Long schoolId, @PathVariable Long teacherId) {
        WeeklyTeacherProgramDTO weeklyProgram = programService.getWeeklyProgramForTeacher(schoolId, teacherId);
        return ResponseEntity.ok(weeklyProgram);
    }
}
