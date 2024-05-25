package com.nbu.schoolbook.user;

import lombok.Getter;

import java.time.LocalDate;

public record UserDTO(
        String email,
        String username,
        String password,
        Long roleId
) {
}
