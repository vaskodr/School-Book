package com.nbu.schoolbook.user.dto;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.role.dto.RoleDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private List<String> roles;
    private Long schoolId;
}
