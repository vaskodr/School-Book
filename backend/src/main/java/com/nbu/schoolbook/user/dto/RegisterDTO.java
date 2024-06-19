package com.nbu.schoolbook.user.dto;

import com.nbu.schoolbook.enums.Gender;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
public class RegisterDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String type;
    @Nullable
    private List<Long> subjectIds;
    @Nullable
    private Long schoolId;
    @Nullable
    private List<Long> gradeIds;
    @Nullable
    private List<Long> absenceIds;
    @Nullable
    private List<Long> parentIds;
    @Nullable
    private Long classId;

}
