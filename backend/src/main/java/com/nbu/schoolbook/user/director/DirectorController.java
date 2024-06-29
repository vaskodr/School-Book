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
@RequestMapping("/api/school/{schoolId}")
@AllArgsConstructor
public class DirectorController {

    private final DirectorService directorService;


    @GetMapping("/director/all")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        List<DirectorDTO> directors = directorService.getAllDirectors();
        return new ResponseEntity<>(directors, HttpStatus.OK);
    }

    @GetMapping("/director/{id}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable Long id) {
        DirectorDTO director = directorService.getDirectorById(id);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @PutMapping("/director/{id}")
    public ResponseEntity<DirectorDTO> updateDirector(@PathVariable Long id, @RequestBody UpdateDirectorDTO updateDirectorDTO) {
        DirectorDTO updatedDirector = directorService.updateDirector(id, updateDirectorDTO);
        return new ResponseEntity<>(updatedDirector, HttpStatus.OK);
    }

    @DeleteMapping("/director/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/director/add-director")
    public ResponseEntity<DirectorDTO> addDirector(@PathVariable Long schoolId, @RequestBody RegisterDTO createDirectorDTO){
        DirectorDTO directorDTO = directorService.registerDirector(createDirectorDTO, schoolId);
        return ResponseEntity.ok(directorDTO);
    }


}
