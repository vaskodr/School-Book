package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProgramService {
    void createProgram(Long schoolId, Long classId);
    //ProgramDTO getProgramById(Long id);
    ProgramDTO getClassProgram(Long schoolId, Long classId);
    List<ProgramDTO> getAllProgramsBySchoolId(Long schoolId);
    ProgramDTO getClassProgramByStudentId(Long schoolId, Long studentId);
    void updateProgram(Long schoolId, Long classId, Long programId, UpdateProgramDTO updateProgramDTO);
    void deleteProgram(Long schoolId, Long classId, Long programId);

    WeeklyProgramDTO getWeeklyProgramForClass(Long schoolId, Long classId);

    WeeklyProgramDTO getWeeklyProgramForStudent(Long schoolId, Long studentId);

    WeeklyTeacherProgramDTO getWeeklyProgramForTeacher(Long schoolId, Long teacherId);
}
