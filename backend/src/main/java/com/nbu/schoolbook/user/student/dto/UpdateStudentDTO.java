package com.nbu.schoolbook.user.student.dto;

import com.nbu.schoolbook.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UpdateStudentDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private String password;
    private List<Long> gradeIds;
    private List<Long> absenceIds;
    private List<Long> parentIds;
    private Long classId;
}
