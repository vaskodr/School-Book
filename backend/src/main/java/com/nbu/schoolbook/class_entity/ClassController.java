package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.class_entity.dto.UpdateClassDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/school/{schoolId}/classes")
public class ClassController {

    private final ClassService classService;

    @PostMapping("/add-class")
    public ResponseEntity<String> addClass(@PathVariable Long schoolId, @RequestBody CreateClassDTO createClassDTO) {
        classService.createClass(schoolId, createClassDTO);
        return ResponseEntity.ok(
                "Class with name: "
                + createClassDTO.getName()
                + " and level: "
                + createClassDTO.getLevel()
                + " created and added successfully to school id: "
                + schoolId
        );
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable Long schoolId,
                                                 @PathVariable Long classId) {
        ClassDTO classDTO = classService.getClassById(schoolId, classId);
        return new ResponseEntity<>(classDTO, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ClassDTO>> getAllClasses(@PathVariable Long schoolId) {
        List<ClassDTO> classes = classService.getAllClasses(schoolId);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @PutMapping("/{classId}")
    public ResponseEntity<String> updateClass(@PathVariable Long schoolId,
                                                @PathVariable Long classId,
                                                @RequestBody UpdateClassDTO updateClassDTO) {
        classService.updateClass(schoolId, classId, updateClassDTO);
        return ResponseEntity.ok(
                "Class with id: "
                        + classId
                        + " in school id: "
                        + schoolId
                        + " updated successfully!"
        );

    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<String> deleteClass(@PathVariable Long schoolId,
                                            @PathVariable Long classId) {
        classService.deleteClass(schoolId, classId);
        return ResponseEntity.ok("Class deleted successfully!");
    }
}
