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
    private String id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private LocalDate dateOfBirth;
    @NotBlank
    private String gender;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;



    @Nullable
    private RegisterDTO registerDTO;
    @Nullable
    private List<Long> subjectIds;
    @Nullable
    private Long classId;
    @Nullable
    private Long schoolId;
}
