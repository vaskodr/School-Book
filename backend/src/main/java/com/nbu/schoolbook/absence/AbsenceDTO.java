package com.nbu.schoolbook.absence;

import java.time.LocalDate;

public record AbsenceDTO(
        Long id,
        String description,
        LocalDate date,
        Long classSessionId,
        Long studentId
) {
}
