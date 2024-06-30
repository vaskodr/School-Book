package com.nbu.schoolbook.user;

import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.dto.RoleDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import com.nbu.schoolbook.user.dto.UserDetailsDTO;
import org.hibernate.sql.Update;

import java.util.List;

public interface UserService {
    RegisterDTO createUser(RegisterDTO registerDTO);
    RegisterDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    UpdateUserDTO updateUser(String id, UpdateUserDTO userDTO);
    void deleteUser(String id);

    UserEntity getUser(RegisterDTO registerDTO, String roleName);

//    SchoolDTO getSchoolByUserId(String userId);
//    UserDetailsDTO getUserDetails(String userId);



}
