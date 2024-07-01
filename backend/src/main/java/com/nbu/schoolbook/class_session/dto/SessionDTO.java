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
    private String startTime;
    private String endTime;
    private String teacher;
    private String subject;
}
