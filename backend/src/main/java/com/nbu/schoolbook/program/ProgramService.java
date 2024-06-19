package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;

import java.util.List;

public interface ProgramService {
    ProgramDTO createProgram(CreateProgramDTO createProgramDTO);
    ProgramDTO getProgramById(Long id);
    List<ProgramDTO> getAllPrograms();
    ProgramDTO updateProgram(Long id, UpdateProgramDTO updateProgramDTO);
    void deleteProgram(Long id);
}
