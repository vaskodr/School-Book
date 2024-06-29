package com.nbu.schoolbook.user.teacher.dto;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTeacherDTO {

    private String firstName;
    private String lastName;

}
