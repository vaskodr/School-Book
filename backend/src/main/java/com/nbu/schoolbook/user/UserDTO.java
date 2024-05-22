package com.nbu.schoolbook.user;

import java.time.LocalDate;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String phone,
        String email,
        Long roleId
) {
}
