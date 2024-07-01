package com.nbu.schoolbook.user.parent;

import java.util.List;
import java.util.stream.Collectors;

import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.UserService;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import com.nbu.schoolbook.user.parent.dto.UpdateParentDTO;
import com.nbu.schoolbook.user.student.StudentMapper;
import com.nbu.schoolbook.user.student.dto.StudentDTO;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    @Override
    public ParentDTO createParent(RegisterDTO registerDTO) {
        UserEntity userEntity = userMapper.mapRegisterDTOToEntity(userService.createUser(registerDTO));
        UserEntity savedUser = userRepository.save(userEntity);

        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setUserEntity(savedUser);
        parentRepository.save(parentEntity);

        return null;
    }

    @Override
    public ParentDTO getParentById(Long id) {
        ParentEntity parentEntity = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
        UserEntity userEntity = parentEntity.getUserEntity();
        return null;
    }

    @Override
    public List<ParentDTO> getAllParents() {
        return null;
    }

    @Override
    public ParentDTO updateParent(Long id, UpdateParentDTO updateParentDTO) {
        ParentEntity parentEntity = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        UserEntity userEntity = parentEntity.getUserEntity();
        if (updateParentDTO.getFirstName() != null) {
            userEntity.setFirstName(updateParentDTO.getFirstName());
        }
        if (updateParentDTO.getLastName() != null) {
            userEntity.setLastName(updateParentDTO.getLastName());
        }
        if (updateParentDTO.getDateOfBirth() != null) {
            userEntity.setDateOfBirth(updateParentDTO.getDateOfBirth());
        }
        if (updateParentDTO.getGender() != null) {
            userEntity.setGender(updateParentDTO.getGender());
        }
        if (updateParentDTO.getPhone() != null) {
            userEntity.setPhone(updateParentDTO.getPhone());
        }
        if (updateParentDTO.getEmail() != null) {
            userEntity.setEmail(updateParentDTO.getEmail());
        }
        if (updateParentDTO.getUsername() != null) {
            userEntity.setUsername(updateParentDTO.getUsername());
        }
        if (updateParentDTO.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(updateParentDTO.getPassword()));
        }

        userRepository.save(userEntity);

        return null;
    }

    @Override
    public void deleteParent(Long id) {
        ParentEntity parentEntity = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
        parentRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> getParentChildrenByUserId(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ParentEntity parentEntity = parentRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        return parentEntity.getStudents().stream()
                .map(studentMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getParents(Long studentId) {

        return null;
    }

    @Transactional
    @Override
    public void unassignParent(Long parentId, Long studentId) {
        ParentEntity parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        // Unassign parent from student's parents set
        student.getParents().remove(parent);
        studentRepository.save(student);

        // Remove the "PARENT" role from the UserEntity
        UserEntity user = parent.getUserEntity();

        RoleEntity parentRole = roleRepository.findByName("ROLE_PARENT");
        parent.getStudents().remove(student);


        if (parent.getStudents().isEmpty()) {
            user.getRoles().remove(parentRole);
        }


        if (parent.getStudents().isEmpty()) {
            parentRepository.delete(parent);
        } else {
            parentRepository.save(parent);
        }

        if (user.getRoles().isEmpty()) {
            // If no roles left, delete the user
            userRepository.delete(user);
        } else {
            // Otherwise, just save the updated user
            userRepository.save(user);
        }
    }
}
