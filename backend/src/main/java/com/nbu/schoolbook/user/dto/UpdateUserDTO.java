package com.nbu.schoolbook.user.dto;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.role.dto.RoleDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private String password;
    private List<RoleDTO> roles;
}
