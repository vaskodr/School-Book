package com.nbu.schoolbook.Mappers;

import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToDTO() {
        UserEntity userEntity = new UserEntity();
        UserDTO userDTO = new UserDTO();
        when(modelMapper.map(userEntity, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userMapper.mapToDTO(userEntity);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(modelMapper, times(1)).map(userEntity, UserDTO.class);
    }

    @Test
    void testMapToEntity() {
        UserDTO userDTO = new UserDTO();
        UserEntity userEntity = new UserEntity();
        when(modelMapper.map(userDTO, UserEntity.class)).thenReturn(userEntity);

        UserEntity result = userMapper.mapToEntity(userDTO);

        assertNotNull(result);
        assertEquals(userEntity, result);
        verify(modelMapper, times(1)).map(userDTO, UserEntity.class);
    }

    @Test
    void testMapToRegisterDTO() {
        UserEntity userEntity = new UserEntity();
        RegisterDTO registerDTO = new RegisterDTO();
        when(modelMapper.map(userEntity, RegisterDTO.class)).thenReturn(registerDTO);

        RegisterDTO result = userMapper.mapToRegisterDTO(userEntity);

        assertNotNull(result);
        assertEquals(registerDTO, result);
        verify(modelMapper, times(1)).map(userEntity, RegisterDTO.class);
    }

    @Test
    void testMapRegisterDTOToEntity() {
        RegisterDTO registerDTO = new RegisterDTO();
        UserEntity userEntity = new UserEntity();
        when(modelMapper.map(registerDTO, UserEntity.class)).thenReturn(userEntity);

        UserEntity result = userMapper.mapRegisterDTOToEntity(registerDTO);

        assertNotNull(result);
        assertEquals(userEntity, result);
        verify(modelMapper, times(1)).map(registerDTO, UserEntity.class);
    }

    @Test
    void testMapEntityToUpdateDTO() {
        UserEntity userEntity = new UserEntity();
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        when(modelMapper.map(userEntity, UpdateUserDTO.class)).thenReturn(updateUserDTO);

        UpdateUserDTO result = userMapper.mapEntityToUpdateDTO(userEntity);

        assertNotNull(result);
        assertEquals(updateUserDTO, result);
        verify(modelMapper, times(1)).map(userEntity, UpdateUserDTO.class);
    }

    @Test
    void testMapUpdateDTOToEntity() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        UserEntity userEntity = new UserEntity();
        when(modelMapper.map(updateUserDTO, UserEntity.class)).thenReturn(userEntity);

        UserEntity result = userMapper.mapUpdateDTOToEntity(updateUserDTO);

        assertNotNull(result);
        assertEquals(userEntity, result);
        verify(modelMapper, times(1)).map(updateUserDTO, UserEntity.class);
    }
}
