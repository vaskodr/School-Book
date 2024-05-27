package com.nbu.schoolbook.school;

import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<CreateSchoolDTO> createSchool(@RequestBody CreateSchoolDTO createSchoolDTO) {
        CreateSchoolDTO createdSchool = schoolService.createSchool(createSchoolDTO);
        return ResponseEntity.ok(createdSchool);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable("id") Long id) {
        SchoolDTO schoolDTO = schoolService.getSchoolById(id);
        return ResponseEntity.ok(schoolDTO);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        List<SchoolDTO> schoolDTOs = schoolService.getAllSchools();
        return ResponseEntity.ok(schoolDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable("id") Long id, @RequestBody SchoolDTO schoolDTO) {
        SchoolDTO updatedSchool = schoolService.updateSchool(id, schoolDTO);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable("id") Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.noContent().build();
    }


}
