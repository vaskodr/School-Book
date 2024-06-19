package com.nbu.schoolbook.grade.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GradeDTO {
    private Long id;
    private BigDecimal grade;
    private LocalDate date;
    private Long studentId;
    private Long classSessionId;
}
