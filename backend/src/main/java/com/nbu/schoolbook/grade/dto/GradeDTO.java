package com.nbu.schoolbook.grade.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeDTO {
    private Long id;
    private BigDecimal grade;
    private LocalDate date;
    private Long studentId;
    private Long subjectId;
    private String subjectName;
}
