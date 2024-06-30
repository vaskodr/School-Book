package com.nbu.schoolbook.program.dto;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.SessionDTO;
import com.nbu.schoolbook.enums.DayOfWeek;
import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class WeeklyProgramDTO {
    private Map<DayOfWeek, List<SessionDTO>> weeklyProgram;
}