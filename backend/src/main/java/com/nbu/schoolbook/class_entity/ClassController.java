package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.class_entity.dto.UpdateClassDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody CreateClassDTO createClassDTO) {
        ClassDTO classDTO = classService.createClass(createClassDTO);
        return new ResponseEntity<>(classDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable Long id) {
        ClassDTO classDTO = classService.getClassById(id);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        List<ClassDTO> classes = classService.getAllClasses();
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable Long id, @RequestBody UpdateClassDTO updateClassDTO) {
        ClassDTO classDTO = classService.updateClass(id, updateClassDTO);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
