package com.nbu.schoolbook.user.teacher.dto;

import com.nbu.schoolbook.enums.Gender;
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
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private List<Long> subjectIds;
    private Long schoolId;
}
