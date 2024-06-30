package com.nbu.schoolbook;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.role.dto.RoleDTO;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.UserServiceImpl;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import com.nbu.schoolbook.user.dto.UserDetailsDTO;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setId("1234567890");
        registerDTO.setFirstName("John");
        registerDTO.setLastName("Doe");
        registerDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        registerDTO.setGender(Gender.MALE.name());
        registerDTO.setPhone("1234567890");
        registerDTO.setEmail("john.doe@example.com");
        registerDTO.setUsername("johndoe");
        registerDTO.setPassword("password");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(registerDTO.getId());
        userEntity.setFirstName(registerDTO.getFirstName());
        userEntity.setLastName(registerDTO.getLastName());
        userEntity.setDateOfBirth(registerDTO.getDateOfBirth());
        userEntity.setGender(Gender.MALE);
        userEntity.setPhone(registerDTO.getPhone());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setPassword("encodedPassword");

        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");
        when(userMapper.mapToRegisterDTO(any(UserEntity.class))).thenReturn(registerDTO);

        RegisterDTO result = userService.createUser(registerDTO);

        assertEquals(registerDTO, result);
        verify(userMapper, times(1)).mapToRegisterDTO(any(UserEntity.class));
    }

    @Test
    void testGetUserById_UserExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1234567890");

        when(userRepository.findById("1234567890")).thenReturn(Optional.of(userEntity));
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1234567890");
        when(userMapper.mapToDTO(userEntity)).thenReturn(userDTO);

        RegisterDTO result = userService.getUserById("1234567890");

        assertEquals(userDTO, result);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById("1234567890")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById("1234567890"));
    }

    @Test
    void testGetAllUsers() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("1234567890");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(userEntity));
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1234567890");
        when(userMapper.mapToDTO(userEntity)).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userDTO, result.get(0));
    }

    public UpdateUserDTO updateUser(String id, UpdateUserDTO userDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(userDTO.getDateOfBirth());
        }
        if (userDTO.getGender() != null) {
            user.setGender(userDTO.getGender());
        }
        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        Set<RoleEntity> roles = new HashSet<>();
        if (userDTO.getRoles() != null) {
            for (RoleDTO roleDTO : userDTO.getRoles()) {
                RoleEntity role = roleRepository.findByName(roleDTO.getName());
                roles.add(role);
            }
        }
        user.setRoles(roles);

        UserEntity updatedUser = userRepository.save(user);
        return userMapper.mapEntityToUpdateDTO(updatedUser);
    }
    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById("1234567890");

        userService.deleteUser("1234567890");

        verify(userRepository, times(1)).deleteById("1234567890");
    }

    @Test
    void testGetUser_UserExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setId("1234567890");

        UserEntity userEntity = new UserEntity();
        userEntity.setId("1234567890");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_TEACHER");

        when(userRepository.findById("1234567890")).thenReturn(Optional.of(userEntity));
        when(roleRepository.findByName("ROLE_TEACHER")).thenReturn(roleEntity);
        when(teacherRepository.existsByUserEntity(userEntity)).thenReturn(false);

        UserEntity result = userService.getUser(registerDTO, "ROLE_TEACHER");

        assertEquals(userEntity, result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testGetUser_UserNotExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setId("1234567890");

        UserEntity userEntity = new UserEntity();
        userEntity.setId("1234567890");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_TEACHER");

        when(userRepository.findById("1234567890")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_TEACHER")).thenReturn(roleEntity);
        when(userMapper.mapRegisterDTOToEntity(registerDTO)).thenReturn(userEntity);

        UserEntity result = userService.getUser(registerDTO, "ROLE_TEACHER");

        assertEquals(userEntity, result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testGetUser_RoleNotFound() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setId("1234567890");

        when(roleRepository.findByName("ROLE_TEACHER")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.getUser(registerDTO, "ROLE_TEACHER"));
    }

    @Test
    void testGetUser_UserAlreadyHasRole() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setId("1234567890");

        UserEntity userEntity = new UserEntity();
        userEntity.setId("1234567890");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_TEACHER");

        when(userRepository.findById("1234567890")).thenReturn(Optional.of(userEntity));
        when(roleRepository.findByName("ROLE_TEACHER")).thenReturn(roleEntity);
        when(teacherRepository.existsByUserEntity(userEntity)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.getUser(registerDTO, "ROLE_TEACHER"));
    }
}
