package com.nbu.schoolbook.program.dto;

import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UpdateProgramDTO {
    private Long classId;
    private List<UpdateClassSessionDTO> classSessions;
}
