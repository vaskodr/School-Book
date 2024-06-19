package com.nbu.schoolbook.user.student.dto;

import com.nbu.schoolbook.enums.Gender;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private String password;
    @Nullable
    private List<Long> gradeIds;
    @Nullable
    private List<Long> absenceIds;
    @Nullable
    private List<Long> parentIds;
    @Nullable
    private Long classId;
}
