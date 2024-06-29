package com.nbu.schoolbook.absence;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;

@Component
public class AbsenceMapper {

    @Autowired
    private ModelMapper modelMapper;

    public AbsenceDTO mapToDTO(AbsenceEntity absenceEntity) {
        return modelMapper.map(absenceEntity, AbsenceDTO.class);
    }

    public AbsenceEntity mapToEntity(AbsenceDTO absenceDTO) {
        return modelMapper.map(absenceDTO, AbsenceEntity.class);
    }

    public AbsenceEntity mapCreateToEntity(CreateAbsenceDTO createAbsenceDTO) {
        return modelMapper.map(createAbsenceDTO, AbsenceEntity.class);
    }
}
