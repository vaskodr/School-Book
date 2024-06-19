package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.director.dto.UpdateDirectorDTO;

import java.util.List;

public interface DirectorService {
    DirectorDTO createDirector(CreateDirectorDTO createDirectorDTO);
    DirectorDTO getDirectorById(Long id);
    List<DirectorDTO> getAllDirectors();
    DirectorDTO updateDirector(Long id, UpdateDirectorDTO updateDirectorDTO);
    void deleteDirector(Long id);
}
