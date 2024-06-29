package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.director.dto.UpdateDirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;

import java.util.List;

public interface DirectorService {
    void registerDirector(RegisterDTO registerDTO, Long schoolId);
    DirectorDTO getDirectorById(Long schoolId, Long id);
    //List<DirectorDTO> getAllDirectors();
    void updateDirector(Long schoolId, Long id, UpdateDirectorDTO updateDirectorDTO);
    void deleteDirector(Long schoolId, Long directorId);

    // TODO: methods assignDirectorToSchool && unassignDirectorFromSchool


    void assignDirectorToSchool(Long directorId, Long schoolId);
    void unassignDirectorFromSchool(Long directorId, Long schoolId);

}
