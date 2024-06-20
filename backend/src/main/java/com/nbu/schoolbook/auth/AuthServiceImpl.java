package com.nbu.schoolbook.auth;

import com.nbu.schoolbook.auth.dto.JwtAuthResponse;
import com.nbu.schoolbook.auth.dto.LoginResponseDTO;
import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.security.JwtTokenProvider;
import com.nbu.schoolbook.user.director.DirectorRepository;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.director.DirectorEntity;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentMapper;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.student.StudentService;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherMapper;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import com.nbu.schoolbook.user.teacher.TeacherService;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TeacherRepository teacherRepository;
    private final DirectorRepository directorRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtAuthResponse login(LoginDTO loginDTO) throws APIException {
        try {
            log.info("Attempting to authenticate user: {}", loginDTO.getUsernameOrEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsernameOrEmail(),
                            loginDTO.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            log.info("User {} authenticated successfully. JWT Token generated.", loginDTO.getUsernameOrEmail());

            Optional<UserEntity> userOptional = userRepository.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail());

            List<String> roles = new ArrayList<>();
            if (userOptional.isPresent()) {
                UserEntity loggedInUser = userOptional.get();
                roles = loggedInUser.getRoles().stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toList());
            }

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setRoles(roles);
            jwtAuthResponse.setAccessToken(token);

            return jwtAuthResponse;

        } catch (Exception e) {
            log.error("Authentication failed for user: {}", loginDTO.getUsernameOrEmail(), e);
            throw new APIException("Invalid username or password");
        }
    }

    @Override
    public String register(RegisterDTO registerDTO) throws APIException {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new APIException("Username already exists!");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new APIException("Email already exists!");
        }

        Set<RoleEntity> roles = new HashSet<>();
        for (String roleName : registerDTO.getRoles()) {
            RoleEntity role = roleRepository.findByName("ROLE_" + roleName.toUpperCase())
                    .orElseThrow(() -> new APIException("Role not found!"));
            roles.add(role);
        }

        UserEntity user = new UserEntity();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setDateOfBirth(registerDTO.getBirthDate());
        user.setGender(registerDTO.getGender());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(roles);

        UserEntity savedUser = userRepository.save(user);

        for (RoleEntity role : roles) {
            String roleName = role.getName().toUpperCase();
            if (roleName.equals("ROLE_TEACHER")) {
                TeacherEntity teacher = new TeacherEntity();
                teacher.setUserEntity(savedUser);
                teacherRepository.save(teacher);
            } else if (roleName.equals("ROLE_DIRECTOR")) {
                DirectorEntity director = new DirectorEntity();
                director.setUserEntity(savedUser);
                directorRepository.save(director);
            } else if (roleName.equals("ROLE_STUDENT")) {
                StudentEntity student = new StudentEntity();
                student.setUserEntity(savedUser);
                studentRepository.save(student);
            }
        }

        return "User registered successfully!";
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}