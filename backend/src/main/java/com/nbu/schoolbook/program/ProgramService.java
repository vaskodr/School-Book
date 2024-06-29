package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.ProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;

import java.util.List;

public interface ProgramService {
    ProgramDTO createProgram(Long schoolId, Long classId, CreateProgramDTO createProgramDTO);
    ProgramDTO getProgramById(Long id);
    List<ProgramDTO> getAllPrograms();
    ProgramDTO getClassProgram(Long schoolId, Long classId);
    ProgramDTO getClassProgramByStudentId(Long schoolId, Long studentId);
    List<ProgramDTO> getAllProgramsBySchoolId(Long schoolId);
    ProgramDTO updateProgram(Long id, UpdateProgramDTO updateProgramDTO);
    void deleteProgram(Long id);


}
