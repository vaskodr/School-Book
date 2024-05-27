package com.nbu.schoolbook.user;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterDTO createUser(RegisterDTO registerDTO) {
        UserEntity user = userMapper.mapRegisterDTOToEntity(registerDTO);
        RoleEntity role = roleRepository.findByName("ROLE_" + registerDTO.getType())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Role does not exists with name: " + registerDTO.getType()
                        )
                );
        user.setRole(role);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.mapToRegisterDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User does not found with id: " + id
                        )
                );
        return userMapper.mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UpdateUserDTO updateUser(Long id, UpdateUserDTO userDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
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

        RoleEntity role = roleRepository.findByName("ROLE_" + userDTO.getRoleName().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role does not exist with name: " + userDTO.getRoleName().toUpperCase()));
        user.setRole(role);

        UserEntity updatedUser = userRepository.save(user);
        return userMapper.mapEntityToUpdateDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
