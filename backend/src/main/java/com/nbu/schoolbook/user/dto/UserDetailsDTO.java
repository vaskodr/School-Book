package com.nbu.schoolbook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Long schoolId;
}
