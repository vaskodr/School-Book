package com.nbu.schoolbook.user.dto;

import com.nbu.schoolbook.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private String roleName;
}
