package com.nbu.schoolbook.program.dto;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDTO {
    private Long id;
    private Long classId;
    private List<ClassSessionDTO> classSessions;
}
