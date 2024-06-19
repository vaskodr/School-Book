package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;

import java.util.List;

public interface AbsenceService {
    AbsenceDTO createAbsence(CreateAbsenceDTO createAbsenceDTO);
    AbsenceDTO getAbsenceById(Long id);
    List<AbsenceDTO> getAllAbsences();
    AbsenceDTO updateAbsence(Long id, UpdateAbsenceDTO updateAbsenceDTO);
    void deleteAbsence(Long id);
}
