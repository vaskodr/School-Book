package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/class-sessions")
public class ClassSessionController {

    private final ClassSessionService classSessionService;

    @PostMapping
    public ResponseEntity<ClassSessionDTO> createClassSession(@RequestBody CreateClassSessionDTO createClassSessionDTO) {
        ClassSessionDTO classSessionDTO = classSessionService.createClassSession(createClassSessionDTO);
        return new ResponseEntity<>(classSessionDTO, HttpStatus.CREATED);
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