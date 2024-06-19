package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.dto.CreateStudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
@AllArgsConstructor
public class StudentMapper {
    private final ModelMapper modelMapper;

    public StudentDTO mapEntityToDTO(StudentEntity student) {
        return modelMapper.map(student, StudentDTO.class);
    }
    public StudentEntity mapDTOToEntity(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, StudentEntity.class);
    }

    public CreateStudentDTO mapEntityToCreateDTO(StudentEntity student) {
        return modelMapper.map(student, CreateStudentDTO.class);
    }
    public StudentEntity mapCreateDTOToEntity(CreateStudentDTO createStudentDTO) {
        return modelMapper.map(createStudentDTO, StudentEntity.class);
    }

    public RegisterDTO mapCreateDTOToRegisterDTO(CreateStudentDTO createStudentDTO) {
        return modelMapper.map(createStudentDTO, RegisterDTO.class);
    }
    public CreateStudentDTO mapRegisterDTOToCreateDTO(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, CreateStudentDTO.class);
    }


}
