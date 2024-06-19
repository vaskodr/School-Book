package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AbsenceMapper {
    private final ModelMapper modelMapper;

    public AbsenceDTO mapToDTO(AbsenceEntity absence) {
        return modelMapper.map(absence, AbsenceDTO.class);
    }

    public AbsenceEntity mapToEntity(AbsenceDTO absenceDTO) {
        return modelMapper.map(absenceDTO, AbsenceEntity.class);
    }
}
