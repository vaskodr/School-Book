package com.nbu.schoolbook.grade.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateGradeDTO {
    private BigDecimal grade;
    private Long studentId;
    private Long classSessionId;
}
