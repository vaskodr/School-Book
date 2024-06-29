package com.nbu.schoolbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SchoolbookApplication {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// The string to be encoded
		String rawPassword = "1234";

		// Encode the string
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// Print the encoded string
		System.out.println("Encoded password: " + encodedPassword);
		SpringApplication.run(SchoolbookApplication.class, args);
	}

}
