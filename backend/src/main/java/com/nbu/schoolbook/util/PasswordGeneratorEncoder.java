package com.nbu.schoolbook.util;

import com.nbu.schoolbook.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@AllArgsConstructor
public class PasswordGeneratorEncoder {

    private static UserRepository userRepository;

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("vasko"));

    }
}
