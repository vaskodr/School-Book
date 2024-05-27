package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SubjectMapper {
    private final ModelMapper modelMapper;

    public CreateSubjectDTO mapEntityToCreateDTO(SubjectEntity subject) {
        return modelMapper.map(subject, CreateSubjectDTO.class);
    }

    public SubjectEntity mapCreateDTOToEntity(CreateSubjectDTO createSubjectDTO) {
        return modelMapper.map(createSubjectDTO, SubjectEntity.class);
    }


    public SubjectDTO mapEntityToDTO(SubjectEntity subject) {
        return modelMapper.map(subject, SubjectDTO.class);
    }
    public SubjectEntity mapDTOToEntity(SubjectDTO subjectDTO) {
        return modelMapper.map(subjectDTO, SubjectEntity.class);
    }
}
