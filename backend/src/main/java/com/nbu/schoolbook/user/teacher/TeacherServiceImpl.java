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
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final SchoolRepository schoolRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public TeacherDTO createTeacher(CreateTeacherDTO createTeacherDTO) {
        TeacherEntity teacherEntity = teacherMapper.mapCreateDTOToEntity(createTeacherDTO);

        // Handle null schoolId
        if (createTeacherDTO.getSchoolId() != null) {
            SchoolEntity school = schoolRepository.findById(createTeacherDTO.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));
            teacherEntity.setSchool(school);
        }

        // Handle null or empty subjectIds
        if (createTeacherDTO.getSubjectIds() != null && !createTeacherDTO.getSubjectIds().isEmpty()) {
            List<SubjectEntity> subjects = subjectRepository.findAllById(createTeacherDTO.getSubjectIds());
            teacherEntity.setSubjects(new HashSet<>(subjects));
        }

        // Fetch and set the ROLE_TEACHER
        RoleEntity role = roleRepository.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        teacherEntity.setRole(role);

        // Encode the password before saving
        teacherEntity.setPassword(passwordEncoder.encode(createTeacherDTO.getPassword()));

        teacherEntity = teacherRepository.save(teacherEntity);

        return teacherMapper.mapEntityToDTO(teacherEntity);
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Teacher not found"
                        )
                );
        return teacherMapper.mapEntityToDTO(teacherEntity);
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        List<TeacherEntity> teacherEntities = teacherRepository.findAllOrderedById();
        return teacherEntities.stream()
                .map(teacherMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO updateTeacher(Long id, UpdateTeacherDTO updateTeacherDTO) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        if (updateTeacherDTO.getSchoolId() != null) {
            SchoolEntity school = schoolRepository.findById(updateTeacherDTO.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));
            teacherEntity.setSchool(school);
        }

        if (updateTeacherDTO.getSubjectIds() != null) {
            // Remove teacher from old subjects
            for (SubjectEntity subject : teacherEntity.getSubjects()) {
                subject.getTeachers().remove(teacherEntity);
            }

            List<SubjectEntity> subjects = subjectRepository.findAllById(updateTeacherDTO.getSubjectIds());
            teacherEntity.setSubjects(new HashSet<>(subjects));

            // Add teacher to new subjects
            for (SubjectEntity subject : teacherEntity.getSubjects()) {
                subject.getTeachers().add(teacherEntity);
            }
        }

        teacherEntity = teacherRepository.save(teacherEntity);

        // Update subjects in the repository
        subjectRepository.saveAll(teacherEntity.getSubjects());

        return teacherMapper.mapEntityToDTO(teacherEntity);
    }

    @Override
    public void deleteTeacher(Long id) {
        TeacherEntity teacher = teacherRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Teacher not found"
                                )
                        );
        teacherRepository.deleteById(id);
    }
}
