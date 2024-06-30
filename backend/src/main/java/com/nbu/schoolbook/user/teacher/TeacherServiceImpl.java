package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.school.*;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectMapper;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.subject.SubjectService;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.UserService;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper;
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;
    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Override
    @Transactional
    public void registerTeacher(RegisterDTO registerDTO, Long schoolId) {
        logger.info("Starting registerTeacher method");

        SchoolEntity school = getSchoolEntity(schoolId);
        if (school == null) {
            logger.error("School not found for id: " + schoolId);
            throw new ResourceNotFoundException("School not found for id: " + schoolId);
        }
        logger.info("School found: " + school.getName());

        ClassEntity mentorClass = getClassIfProvided(registerDTO);
        if (mentorClass != null) {
            logger.info("Mentor class provided: " + mentorClass.getName());
        }

        UserEntity user = getUser(registerDTO, "ROLE_TEACHER");
        if (user == null) {
            logger.error("User could not be created or found for registration DTO: " + registerDTO);
            throw new RuntimeException("User could not be created or found");
        }
        logger.info("User created/found: " + user.getUsername());

        TeacherEntity teacher = createTeacherEntity(user, school);
        if (teacher == null) {
            logger.error("Teacher entity could not be created for user: " + user.getUsername());
            throw new RuntimeException("Teacher entity could not be created");
        }
        logger.info("Teacher entity created for user: " + user.getUsername());

        if (mentorClass != null) {
            assignClassMentor(teacher, mentorClass);
            logger.info("Assigned mentor class to teacher: " + mentorClass.getName());
        }

        handleSubjectAssociation(registerDTO, teacher);
        logger.info("Handled subject association for teacher: " + teacher.getUserEntity().getUsername());

        logger.info("Completed registerTeacher method");
    }

    @Override
    public TeacherDTO getTeacherById(Long schoolId, Long teacherId) {
        TeacherEntity teacherEntity = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        if (!teacherEntity.getSchool().getId().equals(schoolId)) {
            throw new ResourceNotFoundException("Teacher not found in the specified school");
        }

        return teacherMapper.mapEntityToDTO(teacherEntity);
    }

    @Override
    public List<TeacherDTO> getAllTeachersBySchoolId(Long schoolId) {
        List<TeacherEntity> teachers = teacherRepository.findBySchoolId(schoolId);
        return teachers.stream()
                .map(teacherMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTeacher(Long schoolId, Long teacherId, UpdateTeacherDTO updateTeacherDTO) {
        TeacherEntity teacherEntity = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        if (!teacherEntity.getSchool().getId().equals(schoolId)) {
            throw new ResourceNotFoundException("Teacher not found in the specified school");
        }

        updateUserEntity(teacherEntity.getUserEntity(), updateTeacherDTO);
        userRepository.save(teacherEntity.getUserEntity());
    }

    @Override
    public void deleteTeacher(Long schoolId, Long teacherId) {
        TeacherEntity teacherEntity = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        if (!teacherEntity.getSchool().getId().equals(schoolId)) {
            throw new ResourceNotFoundException("Teacher not found in the specified school");
        }

        teacherRepository.deleteById(teacherId);
    }



    @Override
    public void assignClassMentor(TeacherEntity teacher, ClassEntity mentorClass) {
        mentorClass.setMentor(teacher);
        teacher.setMentoredClass(mentorClass);
        classRepository.save(mentorClass);
        teacherRepository.save(teacher);
    }

    @Override
    public void handleSubjectAssociation(RegisterDTO registerDTO, TeacherEntity teacher) {
        if (registerDTO.getSubjectIds() != null) {
            Set<SubjectEntity> subjects = new HashSet<>(subjectRepository.findAllById(registerDTO.getSubjectIds()));
            teacher.setSubjects(subjects);
        }
        teacherRepository.save(teacher);
    }


    private SchoolEntity getSchoolEntity(Long schoolId) {
        return schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found!"));
    }

    private void updateUserEntity(UserEntity userEntity, UpdateTeacherDTO updateTeacherDTO) {
        if (updateTeacherDTO.getFirstName() != null) userEntity.setFirstName(updateTeacherDTO.getFirstName());
        if (updateTeacherDTO.getLastName() != null) userEntity.setLastName(updateTeacherDTO.getLastName());
        if (updateTeacherDTO.getDateOfBirth() != null) userEntity.setDateOfBirth(updateTeacherDTO.getDateOfBirth());
        if (updateTeacherDTO.getGender() != null) userEntity.setGender(updateTeacherDTO.getGender());
        if (updateTeacherDTO.getPhone() != null) userEntity.setPhone(updateTeacherDTO.getPhone());
        if (updateTeacherDTO.getEmail() != null) userEntity.setEmail(updateTeacherDTO.getEmail());
        if (updateTeacherDTO.getUsername() != null) userEntity.setUsername(updateTeacherDTO.getUsername());
        if (updateTeacherDTO.getPassword() != null) userEntity.setPassword(passwordEncoder.encode(updateTeacherDTO.getPassword()));
    }

    private ClassEntity getClassIfProvided(RegisterDTO registerDTO) {
        if (registerDTO.getClassId() != null) {
            ClassEntity mentorClass = classRepository.findById(registerDTO.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found!"));

            if (mentorClass.getMentor() != null) {
                throw new RuntimeException("Class already has a mentor!");
            }
            return mentorClass;
        }
        return null;
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
            if (user.getRoles().contains(role) && teacherRepository.existsByUserEntity(user)) {
                throw new RuntimeException("User is already a " + roleName.toLowerCase() + "!");
            }
            if (user.getRoles() == null) {
                user.setRoles(new HashSet<>());
            }
            user.getRoles().add(role);
        } else {
            user = userMapper.mapRegisterDTOToEntity(registerDTO);
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
        }
        return userRepository.save(user);
    }

    private TeacherEntity createTeacherEntity(UserEntity user, SchoolEntity school) {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setUserEntity(user);
        teacher.setSchool(school);
        teacher.setSubjects(new HashSet<>());
        teacher.setClassSessions(new HashSet<>());

        TeacherEntity savedTeacher = teacherRepository.save(teacher);
        school.getTeachers().add(savedTeacher);
        schoolRepository.save(school);
        return savedTeacher;
    }
}
