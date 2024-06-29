package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AbsenceMapper {
    private final ModelMapper modelMapper;
}
