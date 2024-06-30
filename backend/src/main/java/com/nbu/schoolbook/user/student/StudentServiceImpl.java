package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.UserService;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.parent.ParentMapper;
import com.nbu.schoolbook.user.parent.ParentRepository;
import com.nbu.schoolbook.user.student.dto.StudentClassDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import com.nbu.schoolbook.user.student.dto.UpdateStudentDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClassRepository classRepository;
    private final RoleRepository roleRepository;
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;

    @Override
    public void registerStudent(RegisterDTO registerDTO, Long schoolId, Long classId) {
        ClassEntity studentClass = getClassEntityForSchool(schoolId, classId);
        UserEntity user = getUser(registerDTO, "ROLE_STUDENT");

        StudentEntity student = createStudentEntity(user, studentClass);
        studentClass.getStudents().add(student);
        classRepository.save(studentClass);
        handleParentAssociation(registerDTO, student);
    }

    @Override
    public StudentDetailsDTO getStudentById(Long schoolId, Long classId, Long studentId) {
        StudentEntity studentEntity = studentRepository.findByStudentIdAndClassIdAndSchoolId(studentId, classId, schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found!"));

        return studentMapper.mapToDetailsDTO(studentEntity);

    }

    @Override
    public List<StudentDTO> getAllStudents(Long schoolId, Long classId) {
        List<StudentEntity> students = studentRepository.findAllByStudentClassSchoolIdAndStudentClassId(schoolId, classId);
        return students.stream()
                .map(studentMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDetailsDTO getStudentDetails(StudentEntity student) {
        StudentDetailsDTO studentDetailsDTO = studentMapper.mapToDetailsDTO(student);

        if (student.getParents() != null) {
            studentDetailsDTO.setParents(
                    student.getParents().stream()
                            .map(parentMapper::mapToDTO)
                            .collect(Collectors.toList())
            );
        } else {
            studentDetailsDTO.setParents(null);
        }

        return studentDetailsDTO;
    }
    public StudentDTO getStudentDetailsById(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return studentMapper.mapToDTO(student);
    }


    @Override
    @Transactional
    public void updateStudent(Long schoolId, Long classId, Long studentId, UpdateStudentDTO updateStudentDTO) {
        StudentEntity studentEntity = studentRepository.findByIdAndStudentClassSchoolIdAndStudentClassId(studentId, schoolId, classId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found in the specified school and class"));

        updateUserEntity(studentEntity.getUserEntity(), updateStudentDTO);
        userRepository.save(studentEntity.getUserEntity());
    }

    @Override
    @Transactional
    public void deleteStudent(Long schoolId, Long classId, Long studentId) {
        boolean exists = studentRepository.existsByIdAndStudentClassSchoolIdAndStudentClassId(studentId, schoolId, classId);
        if (!exists) {
            throw new ResourceNotFoundException("Student not found in the specified school and class");
        }
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        studentRepository.deleteById(studentId);
        userRepository.deleteById(studentEntity.getUserEntity().getId());
    }

    @Transactional
    @Override
    public void enrollStudent(Long studentId, Long classId) {
        ClassEntity classEntity = getClassEntity(classId);
        StudentEntity studentEntity = getStudentEntity(studentId);

        if (studentEntity.getStudentClass() == null) {
            classEntity.getStudents().add(studentEntity);
            studentEntity.setStudentClass(classEntity);
            classRepository.save(classEntity);
            studentRepository.save(studentEntity);
        } else {
            throw new RuntimeException("Student has been already assigned to a class!");
        }
    }

    @Transactional
    @Override
    public void unenrollStudent(Long studentId, Long classId) {
        ClassEntity classEntity = getClassEntity(classId);
        StudentEntity studentEntity = getStudentEntity(studentId);

        if (studentEntity.getStudentClass() != null) {
            classEntity.getStudents().remove(studentEntity);
            studentEntity.setStudentClass(null);

            classRepository.save(classEntity);
            studentRepository.save(studentEntity);
        } else {
            throw new RuntimeException("Student is not in a class yet!");
        }
    }

    public StudentClassDTO getStudentByUserId(String userId) {
        StudentEntity studentEntity = studentRepository.findByUserEntityId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user id: " + userId));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        StudentClassDTO studentDTO = new StudentClassDTO();
        studentDTO.setId(studentEntity.getId());
        ClassDTO classDTO = getClassByStudentId(studentEntity.getId());
        studentDTO.setClassDTO(classDTO);
        return studentDTO;
    }

    public ClassDTO getClassByStudentId(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        ClassEntity classEntity = studentEntity.getStudentClass();
        ClassDTO classDTO = new ClassDTO();
        classDTO.setId(classEntity.getId());
        classDTO.setName(classEntity.getName());
        classDTO.setLevel(classEntity.getLevel());

        return classDTO;
    }




    private ClassEntity getClassEntity(Long classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));
    }

    private UserEntity getUser(RegisterDTO registerDTO, String roleName) {
        Optional<UserEntity> userOptional = userRepository.findById(registerDTO.getId());
        RoleEntity role = roleRepository.findByName(roleName);

        if (role == null) {
            throw new RuntimeException(roleName + " role not found!");
        }

        UserEntity user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getRoles().contains(role) && studentRepository.existsByUserEntity(user)) {
                throw new RuntimeException("User is already a " + roleName.toLowerCase() + "!");
            }
            user.getRoles().add(role);
        } else {
            user = userMapper.mapRegisterDTOToEntity(registerDTO);
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
        }
        return userRepository.save(user);
    }

    private StudentEntity createStudentEntity(UserEntity user, ClassEntity studentClass) {
        StudentEntity student = new StudentEntity();
        student.setUserEntity(user);
        student.setStudentClass(studentClass);
        student.setParents(new HashSet<>());
        return studentRepository.save(student);
    }

    private void handleParentAssociation(RegisterDTO registerDTO, StudentEntity student) {
        if (registerDTO.getRegisterDTO() != null) {
            RegisterDTO parentDTO = registerDTO.getRegisterDTO();
            Optional<UserEntity> parentOptional = userRepository.findById(parentDTO.getId());

            ParentEntity parent;
            if (parentOptional.isPresent()) {
                UserEntity parentUser = parentOptional.get();

                // Check if the user already has the parent role
                boolean hasParentRole = parentUser.getRoles().stream()
                        .anyMatch(role -> role.getName().equals("ROLE_PARENT"));

                if (!hasParentRole) {
                    // Add parent role to the user if they don't have it yet
                    RoleEntity parentRole = roleRepository.findByName("ROLE_PARENT");
                    if (parentRole == null) {
                        throw new RuntimeException("Parent role not found!");
                    }
                    parentUser.getRoles().add(parentRole);
                    userRepository.save(parentUser);
                }

                // Fetch the parent entity or create it if it doesn't exist
                parent = parentRepository.findByUserEntity(parentUser)
                        .orElseGet(() -> {
                            ParentEntity newParent = new ParentEntity();
                            newParent.setUserEntity(parentUser);
                            newParent.setStudents(new HashSet<>());
                            return parentRepository.save(newParent);
                        });

                parent.getStudents().add(student);

            } else {
                // Create new parent user and assign the parent role
                UserEntity parentUser = userMapper.mapRegisterDTOToEntity(parentDTO);
                parentUser.setPassword(passwordEncoder.encode(parentDTO.getPassword())); // Encode the password
                RoleEntity parentRole = roleRepository.findByName("ROLE_PARENT");
                if (parentRole == null) {
                    throw new RuntimeException("Parent role not found!");
                }
                parentUser.getRoles().add(parentRole);
                userRepository.save(parentUser);

                // Create new parent entity
                parent = new ParentEntity();
                parent.setUserEntity(parentUser);
                parent.setStudents(new HashSet<>());
                parent.getStudents().add(student);
                parentRepository.save(parent);
            }

            student.getParents().add(parent);
            studentRepository.save(student);
        }
    }

    private void updateUserEntity(UserEntity userEntity, UpdateStudentDTO updateStudentDTO) {
        if (updateStudentDTO.getFirstName() != null) userEntity.setFirstName(updateStudentDTO.getFirstName());
        if (updateStudentDTO.getLastName() != null) userEntity.setLastName(updateStudentDTO.getLastName());
        if (updateStudentDTO.getPhone() != null) userEntity.setPhone(updateStudentDTO.getPhone());
        if (updateStudentDTO.getEmail() != null) userEntity.setEmail(updateStudentDTO.getEmail());
        if (updateStudentDTO.getUsername() != null) userEntity.setUsername(updateStudentDTO.getUsername());
        if (updateStudentDTO.getPassword() != null)
            userEntity.setPassword(passwordEncoder.encode(updateStudentDTO.getPassword()));
    }



    private ClassEntity getClassEntityForSchool(Long schoolId, Long classId) {
        return classRepository.findByIdAndSchoolId(classId, schoolId)
                .orElseThrow(() -> new RuntimeException("Class not found for the given school"));
    }

    private StudentEntity getStudentEntity(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }
}

