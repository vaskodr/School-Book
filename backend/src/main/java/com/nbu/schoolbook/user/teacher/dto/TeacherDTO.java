package com.nbu.schoolbook.user.teacher.dto;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.enums.Gender;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<String> subjectNames;
    private String schoolName;
    @Nullable
    private ClassDTO classDTO;
}
