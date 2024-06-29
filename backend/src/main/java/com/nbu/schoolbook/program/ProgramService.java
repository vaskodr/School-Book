package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.ProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;

import java.util.List;

public interface ProgramService {
    void createProgram(Long schoolId, Long classId, CreateProgramDTO createProgramDTO);
    //ProgramDTO getProgramById(Long id);
    ProgramDTO getClassProgram(Long schoolId, Long classId);
    List<ProgramDTO> getAllProgramsBySchoolId(Long schoolId);
    ProgramDTO getClassProgramByStudentId(Long schoolId, Long studentId);
    void updateProgram(Long schoolId, Long classId, Long programId, UpdateProgramDTO updateProgramDTO);
    void deleteProgram(Long schoolId, Long classId, Long programId);


}
