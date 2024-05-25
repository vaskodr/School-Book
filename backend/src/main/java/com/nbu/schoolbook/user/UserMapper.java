package com.nbu.schoolbook.user;

import com.nbu.schoolbook.user.UserDTO;
import com.nbu.schoolbook.user.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO mapToDTO(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserEntity mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }


}
