package com.nbu.schoolbook.controller.auth;

import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.user.LoginDTO;
import com.nbu.schoolbook.user.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO) throws APIException;
}
