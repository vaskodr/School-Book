package com.nbu.schoolbook.user;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    RegisterDTO createUser(RegisterDTO registerDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);

}
