package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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


    public SubjectDTO mapEntityToDTO(SubjectEntity subjectEntity) {
        SubjectDTO subjectDTO = modelMapper.map(subjectEntity, SubjectDTO.class);
        subjectDTO.setTeacherIds(
                subjectEntity.getTeachers().stream()
                        .map(TeacherEntity::getId)
                        .collect(Collectors.toList())
        );
        return subjectDTO;
    }
    public SubjectEntity mapDTOToEntity(SubjectDTO subjectDTO) {
        return modelMapper.map(subjectDTO, SubjectEntity.class);
    }
}
