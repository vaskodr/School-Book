package com.nbu.schoolbook.auth;

import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.director.DirectorEntity;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        ));

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);


        return "User Logged-In successfully!";
    }

    @Override
    public String register(RegisterDTO registerDTO) throws APIException {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new APIException("Username already exists!");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new APIException("Email already exists!");
        }

        UserEntity user;
        RoleEntity role;

        switch (registerDTO.getType().toLowerCase()) {
            case "student":
                user = new StudentEntity();
                role = roleRepository.findByName("ROLE_STUDENT")
                        .orElseThrow(() -> new APIException("Role not found!"));
                break;
            case "teacher":
                user = new TeacherEntity();
                role = roleRepository.findByName("ROLE_TEACHER")
                        .orElseThrow(() -> new APIException("Role not found!"));
                break;
            case "parent":
                user = new ParentEntity();
                role = roleRepository.findByName("ROLE_PARENT")
                        .orElseThrow(() -> new APIException("Role not found!"));
                break;
            case "director":
                user = new DirectorEntity();
                role = roleRepository.findByName("ROLE_DIRECTOR")
                        .orElseThrow(() -> new APIException("Role not found!"));
                break;
            case "admin":
                user = new UserEntity();
                role = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new APIException("Role not found!"));
                break;
            default:
                throw new APIException("Invalid user type!");
        }

        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setDateOfBirth(registerDTO.getBirthDate());
        user.setGender(registerDTO.getGender());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return "User registered successfully!";
    }
}
