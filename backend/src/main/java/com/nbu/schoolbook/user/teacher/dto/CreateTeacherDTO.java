package com.nbu.schoolbook.user.teacher.dto;

import com.nbu.schoolbook.enums.Gender;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private String password;
    @Nullable
    private List<Long> subjectIds;
    @Nullable
    private Long schoolId;

}
