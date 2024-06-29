package com.nbu.schoolbook.auth;

import com.nbu.schoolbook.auth.dto.JwtAuthResponse;
import com.nbu.schoolbook.auth.dto.LoginResponseDTO;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.exception.APIException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.security.JwtTokenProvider;
import com.nbu.schoolbook.user.UserService;
import com.nbu.schoolbook.user.director.DirectorRepository;
import com.nbu.schoolbook.user.dto.AdminRegistrationDTO;
import com.nbu.schoolbook.user.dto.LoginDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.director.DirectorEntity;
import com.nbu.schoolbook.user.dto.UserDetailsDTO;
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

import javax.management.relation.Role;
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
    private final UserService userService;

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
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

            if (userOptional.isPresent()) {
                UserEntity loggedInUser = userOptional.get();
                roles = loggedInUser.getRoles().stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toList());

                userDetailsDTO.setId(loggedInUser.getId());
                userDetailsDTO.setUsername(loggedInUser.getUsername());
                userDetailsDTO.setEmail(loggedInUser.getEmail());
                userDetailsDTO.setFirstName(loggedInUser.getFirstName());
                userDetailsDTO.setLastName(loggedInUser.getLastName());

                Long schoolId = null;

                if (roles.contains("ROLE_STUDENT")) {
                    Optional<StudentEntity> studentEntityOptional = studentRepository.findByUserEntityId(loggedInUser.getId());
                    if (studentEntityOptional.isPresent()) {
                        schoolId = studentEntityOptional.get().getStudentClass().getSchool().getId();
                    }
                } else if (roles.contains("ROLE_TEACHER")) {
                    Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findByUserEntityId(loggedInUser.getId());
                    if (teacherEntityOptional.isPresent()) {
                        schoolId = teacherEntityOptional.get().getSchool().getId();
                    }
                } else if (roles.contains("ROLE_ADMIN")) {
                    // Handle admin case if needed
                }

                userDetailsDTO.setSchoolId(schoolId);
            }

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setRoles(roles);
            jwtAuthResponse.setAccessToken(token);
            jwtAuthResponse.setUserDetailsDTO(userDetailsDTO);

            return jwtAuthResponse;

        } catch (Exception e) {
            log.error("Authentication failed for user: {}", loginDTO.getUsernameOrEmail(), e);
            throw new APIException("Invalid username or password");
        }
    }

    @Override
    public String register(AdminRegistrationDTO adminRegistrationDTO) throws APIException {
        if (userRepository.existsByUsername(adminRegistrationDTO.getUsername())) {
            throw new APIException("Username already exists!");
        }
        if (userRepository.existsByEmail(adminRegistrationDTO.getEmail())) {
            throw new APIException("Email already exists!");
        }

        Set<RoleEntity> roles = new HashSet<>();
        for (String roleName : adminRegistrationDTO.getRoles()) {
            RoleEntity role = roleRepository.findByName("ROLE_" + roleName.toUpperCase());
            roles.add(role);
        }

        UserEntity user = new UserEntity();
        user.setId(adminRegistrationDTO.getId());
        user.setFirstName(adminRegistrationDTO.getFirstName());
        user.setLastName(adminRegistrationDTO.getLastName());
        user.setDateOfBirth(adminRegistrationDTO.getDateOfBirth());
        user.setGender(Gender.valueOf(adminRegistrationDTO.getGender()));
        user.setPhone(adminRegistrationDTO.getPhone());
        user.setEmail(adminRegistrationDTO.getEmail());
        user.setUsername(adminRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(adminRegistrationDTO.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}