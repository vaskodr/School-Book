package com.nbu.schoolbook.class_session.dto;

import com.nbu.schoolbook.enums.ClassLevel;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSessionDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private String subject;
    private String className;
    private ClassLevel classLevel;
}