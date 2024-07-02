package com.nbu.schoolbook.Services;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.UserServiceImpl;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
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

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_UserNotFound() {
        String userId = "1234567890";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testGetUserById_UserFound() {
        String userId = "1234567890";
        UserEntity userEntity = new UserEntity();
        RegisterDTO registerDTO = new RegisterDTO();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToRegisterDTO(userEntity)).thenReturn(registerDTO);

        RegisterDTO result = userService.getUserById(userId);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAllUsers() {
        List<UserEntity> userList = Arrays.asList(new UserEntity(), new UserEntity());
        List<UserDTO> userDTOList = Arrays.asList(new UserDTO(), new UserDTO());
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.mapToDTO(any(UserEntity.class))).thenReturn(userDTOList.get(0), userDTOList.get(1));

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_UserNotFound() {
        String userId = "1234567890";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, updateUserDTO));
    }

    @Test
    void testUpdateUser_UserFound() {
        String userId = "1234567890";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setRoles(new ArrayList<>()); // Ensure roles are initialized
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.mapEntityToUpdateDTO(any(UserEntity.class))).thenReturn(updateUserDTO);

        UpdateUserDTO result = userService.updateUser(userId, updateUserDTO);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }


    @Test
    void testDeleteUser() {
        String userId = "1234567890";

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
