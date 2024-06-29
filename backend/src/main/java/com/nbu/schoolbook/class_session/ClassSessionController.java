package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping("/api/class-sessions")
public class ClassSessionController {

    private final ClassSessionService classSessionService;
    private final ClassSessionMapper classSessionMapper;

    @PostMapping("/program/{programId}/create")
    public ResponseEntity<ClassSessionDTO> createClassSession(
            @PathVariable Long programId,
            @RequestBody CreateClassSessionDTO createClassSessionDTO) {
        ClassSessionDTO classSessionDTO = classSessionService.createClassSession(createClassSessionDTO, programId);
        return ResponseEntity.status(HttpStatus.CREATED).body(classSessionDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSessionDTO> getClassSessionById(@PathVariable Long id) {
        ClassSessionDTO classSessionDTO = classSessionService.getClassSessionById(id);
        return new ResponseEntity<>(classSessionDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClassSessionDTO>> getAllClassSessions() {
        List<ClassSessionDTO> classSessions = classSessionService.getAllClassSessions();
        return new ResponseEntity<>(classSessions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSessionDTO> updateClassSession(@PathVariable Long id, @RequestBody UpdateClassSessionDTO updateClassSessionDTO) {
        ClassSessionDTO classSessionDTO = classSessionService.updateClassSession(id, updateClassSessionDTO);
        return new ResponseEntity<>(classSessionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSession(@PathVariable Long id) {
        classSessionService.deleteClassSession(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}