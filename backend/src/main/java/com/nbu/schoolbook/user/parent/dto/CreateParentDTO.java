package com.nbu.schoolbook.user.parent.dto;

import com.nbu.schoolbook.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateParentDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private String password;
}
