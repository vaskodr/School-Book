package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.dto.CreateStudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
@AllArgsConstructor
public class StudentMapper {
    private final ModelMapper modelMapper;

    public StudentDTO mapToDTO(StudentEntity student) {
        return modelMapper.map(student, StudentDTO.class);
    }

    public StudentDetailsDTO mapToDetailsDTO(StudentEntity student){
        return modelMapper.map(student, StudentDetailsDTO.class);
    }

}
