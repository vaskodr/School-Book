package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectMapper;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.subject.SubjectService;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TeacherDTO createTeacher(CreateTeacherDTO createTeacherDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(createTeacherDTO.getFirstName());
        userEntity.setLastName(createTeacherDTO.getLastName());
        userEntity.setDateOfBirth(createTeacherDTO.getDateOfBirth());
        userEntity.setGender(createTeacherDTO.getGender());
        userEntity.setPhone(createTeacherDTO.getPhone());
        userEntity.setEmail(createTeacherDTO.getEmail());
        userEntity.setUsername(createTeacherDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(createTeacherDTO.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);

        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setUserEntity(savedUser);

        teacherEntity = teacherRepository.save(teacherEntity);

        return new TeacherDTO(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getDateOfBirth(), savedUser.getGender(), savedUser.getPhone(), savedUser.getEmail(), savedUser.getUsername(), createTeacherDTO.getSubjectIds(), createTeacherDTO.getSchoolId());
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        UserEntity userEntity = teacherEntity.getUserEntity();
        return new TeacherDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername(), null, null);
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherEntity -> {
                    UserEntity userEntity = teacherEntity.getUserEntity();
                    return new TeacherDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername(), null, null);
                })
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO updateTeacher(Long id, UpdateTeacherDTO updateTeacherDTO) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        UserEntity userEntity = teacherEntity.getUserEntity();
        if (updateTeacherDTO.getFirstName() != null) userEntity.setFirstName(updateTeacherDTO.getFirstName());
        if (updateTeacherDTO.getLastName() != null) userEntity.setLastName(updateTeacherDTO.getLastName());
        if (updateTeacherDTO.getDateOfBirth() != null) userEntity.setDateOfBirth(updateTeacherDTO.getDateOfBirth());
        if (updateTeacherDTO.getGender() != null) userEntity.setGender(updateTeacherDTO.getGender());
        if (updateTeacherDTO.getPhone() != null) userEntity.setPhone(updateTeacherDTO.getPhone());
        if (updateTeacherDTO.getEmail() != null) userEntity.setEmail(updateTeacherDTO.getEmail());
        if (updateTeacherDTO.getUsername() != null) userEntity.setUsername(updateTeacherDTO.getUsername());
        if (updateTeacherDTO.getPassword() != null) userEntity.setPassword(passwordEncoder.encode(updateTeacherDTO.getPassword()));

        userRepository.save(userEntity);

        return new TeacherDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername(), updateTeacherDTO.getSubjectIds(), updateTeacherDTO.getSchoolId());
    }

    @Override
    public void deleteTeacher(Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        teacherRepository.deleteById(id);
    }
}
