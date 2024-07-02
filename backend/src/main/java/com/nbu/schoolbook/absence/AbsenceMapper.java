package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
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
        AbsenceDTO absenceDTO = new AbsenceDTO();
        absenceDTO.setId(absenceEntity.getId());
        absenceDTO.setDate(absenceEntity.getDate());
        absenceDTO.setDescription(absenceEntity.getDescription());
        absenceDTO.setStudentId(absenceEntity.getStudent().getId());
        absenceDTO.setClassSessionId(absenceEntity.getClassSession().getId());
        ClassSessionDTO classSessionDTO = new ClassSessionDTO();
        classSessionDTO.setId(absenceEntity.getClassSession().getId());
        classSessionDTO.setDay(absenceEntity.getClassSession().getDay());
        classSessionDTO.setSubjectId(absenceEntity.getClassSession().getSubject().getId());
        classSessionDTO.setSubjectName(absenceEntity.getClassSession().getSubject().getName());
        absenceDTO.setClassSessionDTO(classSessionDTO);
        return absenceDTO;
    }

    public AbsenceEntity mapToEntity(AbsenceDTO absenceDTO) {
        return modelMapper.map(absenceDTO, AbsenceEntity.class);
    }

    public AbsenceEntity mapCreateToEntity(CreateAbsenceDTO createAbsenceDTO) {
        return modelMapper.map(createAbsenceDTO, AbsenceEntity.class);
    }
}
