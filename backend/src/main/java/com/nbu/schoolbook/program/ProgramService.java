package com.nbu.schoolbook.program;

import java.util.List;

public interface ProgramService {
    ProgramEntity saveProgram(ProgramEntity program);
    ProgramEntity updateProgram(long id, ProgramEntity program);
    void deleteProgram(long id);
    ProgramEntity getProgramById(long id);
    List<ProgramEntity> getAllPrograms();
}
