package com.nbu.schoolbook.auth.dto;

import com.nbu.schoolbook.user.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private List<String> roles;
    private UserDetailsDTO userDetailsDTO;
}
