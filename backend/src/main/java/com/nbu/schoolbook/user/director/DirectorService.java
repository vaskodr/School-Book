package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.director.dto.UpdateDirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;

import java.util.List;

public interface DirectorService {
    DirectorDTO registerDirector(RegisterDTO registerDTO, Long schoolId);
    DirectorDTO getDirectorById(Long id);
    List<DirectorDTO> getAllDirectors();
    DirectorDTO updateDirector(Long id, UpdateDirectorDTO updateDirectorDTO);
    void deleteDirector(Long id);

    // TODO: methods assignDirectorToSchool && unassignDirectorFromSchool


    void assignDirectorToSchool(Long directorId, Long schoolId);
    void unassignDirectorFromSchool(Long directorId, Long schoolId);

}
