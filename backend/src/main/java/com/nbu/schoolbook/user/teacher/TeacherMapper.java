package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TeacherMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public TeacherDTO mapEntityToDTO(TeacherEntity teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setFirstName(teacher.getUserEntity().getFirstName());
        teacherDTO.setLastName(teacher.getUserEntity().getLastName());
        teacherDTO.setDateOfBirth(teacher.getUserEntity().getDateOfBirth());
        teacherDTO.setGender(teacher.getUserEntity().getGender());
        teacherDTO.setPhone(teacher.getUserEntity().getPhone());
        teacherDTO.setEmail(teacher.getUserEntity().getEmail());
        teacherDTO.setUsername(teacher.getUserEntity().getUsername());

        if (teacher.getSubjects() != null) {
            teacherDTO.setSubjectNames(teacher.getSubjects().stream()
                    .map(SubjectEntity::getName)  // Assuming SubjectEntity has a getName() method
                    .collect(Collectors.toList()));
        }

        teacherDTO.setSchoolName(teacher.getSchool().getName());
        if (teacher.getMentoredClass() != null) {
            ClassEntity classEntity = classRepository.findById(teacher.getMentoredClass().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Class not found!")
                    );
            ClassDTO classDTO = modelMapper.map(classEntity, ClassDTO.class);

            teacherDTO.setClassDTO(classDTO);
        } else {
            teacherDTO.setClassDTO(null);
        }


        return teacherDTO;
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

    public TeacherEntity mapRegisterDTOToEntity(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, TeacherEntity.class);
    }

    public RegisterDTO mapCreateDTOToRegisterDTO(CreateTeacherDTO createTeacherDTO) {
        return modelMapper.map(createTeacherDTO, RegisterDTO.class);
    }
    public CreateTeacherDTO mapRegisterDTOToCreateDTO(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, CreateTeacherDTO.class);
    }
}
