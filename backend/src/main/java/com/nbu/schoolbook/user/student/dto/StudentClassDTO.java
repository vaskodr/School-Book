package com.nbu.schoolbook.user.student.dto;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassDTO {
    private Long id;
    private ClassDTO classDTO;
}
