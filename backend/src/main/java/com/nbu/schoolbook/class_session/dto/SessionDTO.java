package com.nbu.schoolbook.class_session.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private String teacher;
    private String subject;
}
