package com.nbu.schoolbook.class_entity.dto;

import com.nbu.schoolbook.enums.ClassLevel;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDetailsDTO {
    private Long id;
    private String name;
    private ClassLevel level;
    private String mentorName;
    private List<StudentDetailsDTO> studentDetailsDTOS;
}
