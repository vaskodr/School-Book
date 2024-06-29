package com.nbu.schoolbook.absence.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbsenceDTO {
    private Long id;
    private String description;
    private LocalDate date;
    private Long classSessionId;
    private Long studentId;
}
