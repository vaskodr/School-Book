package com.nbu.schoolbook.grade.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGradeDTO {
    private BigDecimal grade;
    private Long studentId;
    private Long subjectId;
    private Long teacherId;
}
