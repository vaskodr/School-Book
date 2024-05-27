package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeacherMapper {
    private final ModelMapper modelMapper;

    public TeacherDTO mapEntityToDTO(TeacherEntity teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }
    public TeacherEntity mapDTOToEntity(TeacherDTO teacherDTO) {
        return modelMapper.map(teacherDTO, TeacherEntity.class);
    }
    public CreateTeacherDTO mapEntityToCreateDTO(TeacherEntity teacher) {
        return modelMapper.map(teacher, CreateTeacherDTO.class);
    }
    public TeacherEntity mapCreateDTOToEntity(CreateTeacherDTO createTeacherDTO) {
        return modelMapper.map(createTeacherDTO, TeacherEntity.class);
    }
    public UpdateTeacherDTO mapEntityToUpdateDTO(TeacherEntity teacher) {
        return modelMapper.map(teacher, UpdateTeacherDTO.class);
    }
    public TeacherEntity mapUpdateDTOToEntity(UpdateTeacherDTO updateTeacherDTO) {
        return modelMapper.map(updateTeacherDTO, TeacherEntity.class);
    }
    public TeacherDTO mapUpdateDTOToDTO(UpdateTeacherDTO updateTeacherDTO) {
        return modelMapper.map(updateTeacherDTO, TeacherDTO.class);
    }
}
