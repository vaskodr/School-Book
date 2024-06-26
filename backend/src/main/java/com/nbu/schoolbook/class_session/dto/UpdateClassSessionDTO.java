package com.nbu.schoolbook.class_session.dto;

import com.nbu.schoolbook.enums.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateClassSessionDTO {
    private Long id;
    private DayOfWeek day;
    private String startTime;
    private String endTime;
    private Long teacherId;
    private Long subjectId;
}
