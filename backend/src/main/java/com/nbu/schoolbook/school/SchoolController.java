package com.nbu.schoolbook.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<SchoolEntity> createSchool(@RequestBody SchoolEntity school) {
        SchoolEntity createdSchool = schoolService.saveSchool(school);
        return ResponseEntity.ok(createdSchool);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolEntity> updateSchool(@PathVariable long id, @RequestBody SchoolEntity school) {
        SchoolEntity updatedSchool = schoolService.updateSchool(id, school);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolEntity> getSchoolById(@PathVariable long id) {
        SchoolEntity school = schoolService.getSchoolById(id);
        return ResponseEntity.ok(school);
    }

    @GetMapping
    public ResponseEntity<List<SchoolEntity>> getAllSchools() {
        List<SchoolEntity> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(schools);
    }
}
