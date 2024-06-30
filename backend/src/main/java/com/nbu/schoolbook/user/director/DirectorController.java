package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.director.dto.UpdateDirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school/{schoolId}/director")
@AllArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-director")
    public ResponseEntity<String> addDirector(@PathVariable Long schoolId,
                                              @RequestBody RegisterDTO createDirectorDTO){
        directorService.registerDirector(createDirectorDTO, schoolId);
        return ResponseEntity.ok("Director created and added successfully!");
    }

    @GetMapping("/{directorId}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable Long schoolId,
                                                       @PathVariable Long directorId) {
        DirectorDTO director = directorService.getDirectorById(schoolId, directorId);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @PutMapping("/{directorId}")
    public ResponseEntity<String> updateDirector(@PathVariable Long schoolId,
                                                 @PathVariable Long directorId,
                                                 @RequestBody UpdateDirectorDTO updateDirectorDTO) {
        directorService.updateDirector(schoolId, directorId, updateDirectorDTO);
        return ResponseEntity.ok("Director updated successfully!");
    }

    @DeleteMapping("/{directorId}")
    public ResponseEntity<String> deleteDirector(@PathVariable Long schoolId,
                                                 @PathVariable Long directorId) {
        directorService.deleteDirector(schoolId, directorId);
        return ResponseEntity.ok("Director deleted successfully!");
    }

    @PostMapping("/{directorId}/assign-director")
    public ResponseEntity<String> assignDirectorToSchool(@PathVariable Long schoolId, @PathVariable Long directorId) {
        directorService.assignDirectorToSchool(directorId, schoolId);
        return ResponseEntity.ok("Director assigned to school successfully");
    }

    @PostMapping("/{directorId}/unassign-director")
    public ResponseEntity<String> unassignDirectorFromSchool(@PathVariable Long schoolId, @PathVariable Long directorId) {
        directorService.unassignDirectorFromSchool(directorId, schoolId);
        return ResponseEntity.ok("Director unassigned from school successfully");
    }




}
