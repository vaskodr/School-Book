package com.nbu.schoolbook.auth.controller;

import com.nbu.schoolbook.auth.dto.JwtAuthResponse;
import com.nbu.schoolbook.auth.dto.LoginResponseDTO;
import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.auth.AuthService;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO) throws APIException {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDTO);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) throws APIException {
        String res = authService.register(registerDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("User logged out successfully!");
    }
}
