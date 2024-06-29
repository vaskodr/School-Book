package com.nbu.schoolbook.user;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.role.dto.RoleDTO;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolMapper;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.user.director.DirectorEntity;
import com.nbu.schoolbook.user.director.DirectorRepository;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import com.nbu.schoolbook.user.dto.UserDetailsDTO;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.parent.ParentRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentMapper;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import com.nbu.schoolbook.user.teacher.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;

    @Override
    public RegisterDTO createUser(RegisterDTO registerDTO) {
        UserEntity user = new UserEntity();
        user.setId(registerDTO.getId());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setDateOfBirth(registerDTO.getDateOfBirth());
        user.setGender(Gender.valueOf(registerDTO.getGender()));
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        return userMapper.mapToRegisterDTO(user);
    }

    @Override
    public UserDTO getUserById(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found with id: " + id
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
    public UpdateUserDTO updateUser(String id, UpdateUserDTO userDTO) {
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

        Set<RoleEntity> roles = new HashSet<>();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            RoleEntity role = roleRepository.findByName(roleDTO.getName());
            roles.add(role);
        }
        user.setRoles(roles);

        UserEntity updatedUser = userRepository.save(user);
        return userMapper.mapEntityToUpdateDTO(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity getUser(RegisterDTO registerDTO, String roleName) {
        Optional<UserEntity> userOptional = userRepository.findById(registerDTO.getId());
        RoleEntity role = roleRepository.findByName(roleName);

        if (role == null) {
            throw new RuntimeException(roleName + " role not found!");
        }

        UserEntity user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getRoles().contains(role) && teacherRepository.existsByUserEntity(user)) {
                throw new RuntimeException("User is already a " + roleName.toLowerCase() + "!");
            }
            if (user.getRoles() == null) {
                user.setRoles(new HashSet<>());
            }
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            user = userMapper.mapRegisterDTOToEntity(registerDTO);
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
            userRepository.save(user);
        }
        return user;
    }

//    public SchoolDTO getSchoolByUserId(String userId) {
//        TeacherEntity teacher = teacherRepository.findByUserEntityId(userId);
//        if (teacher != null) {
//            return schoolMapper.mapToDTO(teacher.getSchool());
//        }
//
//        StudentEntity student = studentRepository.findByUserEntityId(userId);
//        if (student != null) {
//            return schoolMapper.mapToDTO(student.getStudentClass().getSchool());
//        }
//
////        ParentEntity parent = parentRepository.findByUserEntityId(userId);
////        if (parent != null) {
////            return schoolMapper.mapToDTO(parent)
////        }
//
//        DirectorEntity director = directorRepository.findByUserEntityId(userId);
//        if (director != null) {
//            return schoolMapper.mapToDTO(director.getSchool());
//        }
//
//        return null; // Or throw an appropriate exception
//    }
//
//
//    public UserDetailsDTO getUserDetails(String userId) {
//        UserEntity userEntity = userRepository.findById(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
//
//        UserDetailsDTO userDTO = new UserDetailsDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setUsername(userEntity.getUsername());
//        userDTO.setEmail(userEntity.getEmail());
//        userDTO.setFirstName(userEntity.getFirstName());
//        userDTO.setLastName(userEntity.getLastName());
//        userDTO.setSchoolId();
//
//        return userDTO;
//    }
}
