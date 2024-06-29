package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassMapper;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
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
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ClassMapper classMapper;


    public StudentDTO mapToDTO(StudentEntity student) {
        UserEntity user = userRepository.findById(student.getUserEntity().getId()).orElseThrow(
                () -> new RuntimeException("User not found!")
        );
        ClassEntity classEntity = classRepository.findById(student.getStudentClass().getId()).orElseThrow(
                () -> new RuntimeException("Class not found!")
        );
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(user.getFirstName());
        studentDTO.setLastName(user.getLastName());
        studentDTO.setStudentClass(classMapper.mapToDTO(classEntity));
        return studentDTO;
    }

    public StudentDetailsDTO mapToDetailsDTO(StudentEntity student){
        return modelMapper.map(student, StudentDetailsDTO.class);
    }

}
