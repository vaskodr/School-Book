package com.nbu.schoolbook.class_session.dto;

import com.nbu.schoolbook.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSessionDTO {
    private Long id;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long teacherId;
    private Long subjectId;
    private Long programId;
}
