package com.nbu.schoolbook.user;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity user = userMapper.mapToEntity(userDTO);
        RoleEntity role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Role does not exists with id: " + userDTO.roleId()
                        )
                );
        user.setRole(role);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.mapToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
