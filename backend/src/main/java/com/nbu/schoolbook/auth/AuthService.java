package com.nbu.schoolbook.auth;

import com.nbu.schoolbook.auth.dto.JwtAuthResponse;
import com.nbu.schoolbook.auth.dto.LoginResponseDTO;
import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;

public interface AuthService {
    JwtAuthResponse login(LoginDTO loginDTO) throws APIException;
    String register(RegisterDTO registerDTO) throws APIException;
    void logout();
}
