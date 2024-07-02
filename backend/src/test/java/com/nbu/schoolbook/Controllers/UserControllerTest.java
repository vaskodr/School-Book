package com.nbu.schoolbook.Controllers;

import com.nbu.schoolbook.user.UserController;
import com.nbu.schoolbook.user.UserService;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        RegisterDTO registerDTO = new RegisterDTO();
        RegisterDTO savedUser = new RegisterDTO();
        when(userService.createUser(any(RegisterDTO.class))).thenReturn(savedUser);

        ResponseEntity<RegisterDTO> response = userController.createUser(registerDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).createUser(any(RegisterDTO.class));
    }

    @Test
    void testGetUserById() {
        String userId = "1234567890";
        RegisterDTO registerDTO = new RegisterDTO();
        when(userService.getUserById(userId)).thenReturn(registerDTO);

        ResponseEntity<RegisterDTO> response = userController.getUserById(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registerDTO, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> userDTOList = Arrays.asList(new UserDTO(), new UserDTO());
        when(userService.getAllUsers()).thenReturn(userDTOList);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTOList, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        String userId = "1234567890";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        UpdateUserDTO updatedUser = new UpdateUserDTO();
        when(userService.updateUser(eq(userId), any(UpdateUserDTO.class))).thenReturn(updatedUser);

        ResponseEntity<UpdateUserDTO> response = userController.updateUser(userId, updateUserDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUser(eq(userId), any(UpdateUserDTO.class));
    }

    @Test
    void testDeleteUser() {
        String userId = "1234567890";

        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User has been deleted successfully!", response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }
}
