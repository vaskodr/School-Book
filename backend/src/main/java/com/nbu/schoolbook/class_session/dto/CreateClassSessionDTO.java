package com.nbu.schoolbook.class_session.dto;

import com.nbu.schoolbook.enums.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateClassSessionDTO {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long teacherId;
    private Long subjectId;
}
