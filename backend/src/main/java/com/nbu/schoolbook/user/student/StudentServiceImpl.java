package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.dto.CreateStudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.UpdateStudentDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(createStudentDTO.getFirstName());
        userEntity.setLastName(createStudentDTO.getLastName());
        userEntity.setDateOfBirth(createStudentDTO.getBirthDate());
        userEntity.setGender(createStudentDTO.getGender());
        userEntity.setPhone(createStudentDTO.getPhone());
        userEntity.setEmail(createStudentDTO.getEmail());
        userEntity.setUsername(createStudentDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(createStudentDTO.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setUserEntity(savedUser);

        studentEntity = studentRepository.save(studentEntity);

        return new StudentDTO(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getDateOfBirth(), savedUser.getGender(), savedUser.getPhone(), savedUser.getEmail(), savedUser.getUsername(), createStudentDTO.getGradeIds(), createStudentDTO.getAbsenceIds(), createStudentDTO.getParentIds(), createStudentDTO.getClassId());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        UserEntity userEntity = studentEntity.getUserEntity();
        return new StudentDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername(), null, null, null, null);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentEntity -> {
                    UserEntity userEntity = studentEntity.getUserEntity();
                    return new StudentDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername(), null, null, null, null);
                })
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(Long id, UpdateStudentDTO updateStudentDTO) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        UserEntity userEntity = studentEntity.getUserEntity();
        if (updateStudentDTO.getFirstName() != null) userEntity.setFirstName(updateStudentDTO.getFirstName());
        if (updateStudentDTO.getLastName() != null) userEntity.setLastName(updateStudentDTO.getLastName());
        if (updateStudentDTO.getDateOfBirth() != null) userEntity.setDateOfBirth(updateStudentDTO.getDateOfBirth());
        if (updateStudentDTO.getGender() != null) userEntity.setGender(updateStudentDTO.getGender());
        if (updateStudentDTO.getPhone() != null) userEntity.setPhone(updateStudentDTO.getPhone());
        if (updateStudentDTO.getEmail() != null) userEntity.setEmail(updateStudentDTO.getEmail());
        if (updateStudentDTO.getUsername() != null) userEntity.setUsername(updateStudentDTO.getUsername());
        if (updateStudentDTO.getPassword() != null)
            userEntity.setPassword(passwordEncoder.encode(updateStudentDTO.getPassword()));

        userRepository.save(userEntity);

        return new StudentDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername(), updateStudentDTO.getGradeIds(), updateStudentDTO.getAbsenceIds(), updateStudentDTO.getParentIds(), updateStudentDTO.getClassId());
    }

    @Override
    public void deleteStudent(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.deleteById(id);
    }
}

