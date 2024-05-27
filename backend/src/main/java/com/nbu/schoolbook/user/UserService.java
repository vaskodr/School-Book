package com.nbu.schoolbook.user;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import org.hibernate.sql.Update;

import java.util.List;

public interface UserService {
    RegisterDTO createUser(RegisterDTO registerDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UpdateUserDTO updateUser(Long id, UpdateUserDTO userDTO);
    void deleteUser(Long id);

}
