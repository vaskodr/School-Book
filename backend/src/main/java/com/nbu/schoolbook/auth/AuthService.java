package com.nbu.schoolbook.auth;

import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO) throws APIException;
}
