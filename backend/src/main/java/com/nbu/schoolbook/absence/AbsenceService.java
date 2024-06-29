package com.nbu.schoolbook.absence;

import java.util.List;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;

public interface AbsenceService {
    List<AbsenceDTO> getAbsencesByStudentId(Long studentId);
    AbsenceDTO createAbsence(CreateAbsenceDTO createAbsenceDTO);
    AbsenceDTO updateAbsence(Long absenceId, UpdateAbsenceDTO updateAbsenceDTO);
}
