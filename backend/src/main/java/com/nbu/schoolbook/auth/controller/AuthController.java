package com.nbu.schoolbook.auth.controller;

import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.auth.AuthService;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String res = authService.login(loginDTO);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) throws APIException {
        String res = authService.register(registerDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
