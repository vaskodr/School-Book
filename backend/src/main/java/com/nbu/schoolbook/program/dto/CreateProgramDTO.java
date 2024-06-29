package com.nbu.schoolbook.program.dto;

import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateProgramDTO {
    private List<CreateClassSessionDTO> classSessions;
}
