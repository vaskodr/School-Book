package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;
import lombok.AllArgsConstructor;
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


    @Override
    public TeacherDTO createTeacher(CreateTeacherDTO createTeacherDTO) {
        TeacherEntity teacherEntity = teacherMapper.mapCreateDTOToEntity(createTeacherDTO);
        SchoolEntity school = schoolRepository.findById(createTeacherDTO.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
        teacherEntity.setSchool(school);
        List<SubjectEntity> subjects = subjectRepository.findAllById(createTeacherDTO.getSubjectIds());
        teacherEntity.setSubjects(new HashSet<>(subjects));
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
        List<TeacherEntity> teacherEntities = teacherRepository.findAll();
        return teacherEntities.stream()
                .map(teacherMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO updateTeacher(Long id, UpdateTeacherDTO updateTeacherDTO) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Teacher not found"
                        )
                );

        if (updateTeacherDTO.getFirstName() != null) {
            teacher.setFirstName(updateTeacherDTO.getFirstName());
        }
        if (updateTeacherDTO.getLastName() != null) {
            teacher.setLastName(updateTeacherDTO.getLastName());
        }
        if (updateTeacherDTO.getDateOfBirth() != null) {
            teacher.setDateOfBirth(updateTeacherDTO.getDateOfBirth());
        }

        if (updateTeacherDTO.getGender() != null) {
            teacher.setGender(updateTeacherDTO.getGender());
        }
        if (updateTeacherDTO.getPhone() != null) {
            teacher.setPhone(updateTeacherDTO.getPhone());
        }
        if (updateTeacherDTO.getEmail() != null) {
            teacher.setEmail(updateTeacherDTO.getEmail());
        }
        if (updateTeacherDTO.getUsername() != null) {
            teacher.setUsername(updateTeacherDTO.getUsername());
        }
        if (updateTeacherDTO.getSchoolId() != null) {
            SchoolEntity school = schoolRepository.findById(updateTeacherDTO.getSchoolId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("School not found")
                    );
            teacher.setSchool(school);
        }
        if (updateTeacherDTO.getSubjectIds() != null) {
            List<SubjectEntity> subjects = subjectRepository.findAllById(updateTeacherDTO.getSubjectIds());
            teacher.setSubjects(new HashSet<>(subjects));
        }
        teacher = teacherRepository.save(teacher);
        return teacherMapper.mapEntityToDTO(teacher);
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
