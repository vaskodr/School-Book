package com.nbu.schoolbook.program.dto;

import com.nbu.schoolbook.class_session.dto.TeacherSessionDTO;
import com.nbu.schoolbook.enums.DayOfWeek;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class WeeklyTeacherProgramDTO {
    private Map<DayOfWeek, List<TeacherSessionDTO>> weeklyProgram;
}
