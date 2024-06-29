package com.nbu.schoolbook.user.student.dto;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private ClassDTO classDTO;
}
