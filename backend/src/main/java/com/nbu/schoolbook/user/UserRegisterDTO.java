package com.nbu.schoolbook.user;

import java.time.LocalDate;

public record UserRegisterDTO(
        String firstName,
        String lastName,
        String phone,
        String username,
        String password
        ) {
}
