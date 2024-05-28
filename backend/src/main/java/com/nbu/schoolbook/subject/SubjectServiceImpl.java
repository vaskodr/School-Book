package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public CreateSubjectDTO saveSubject(CreateSubjectDTO createSubjectDTO) {
        SubjectEntity subject = subjectMapper.mapCreateDTOToEntity(createSubjectDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.mapEntityToCreateDTO(subject);

    }

    @Override
    public SubjectDTO getSubjectById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Subject does not exists with id: " + id
                        )
                );
        return subjectMapper.mapEntityToDTO(subject);
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
         List<SubjectEntity> subjects = subjectRepository.findAll();
         return subjects.stream()
                 .map(subjectMapper::mapEntityToDTO)
                 .collect(Collectors.toList());
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject does not exists with id: " + id));

        if (subjectDTO.getName() != null) {
            subject.setName(subjectDTO.getName());
        }

        if (subjectDTO.getTeacherIds() != null) {
            Set<TeacherEntity> teachers = new HashSet<>(teacherRepository.findAllById(subjectDTO.getTeacherIds()));
            subject.setTeachers(teachers);
        }

        SubjectEntity updatedSubject = subjectRepository.save(subject);
        return subjectMapper.mapEntityToDTO(updatedSubject);
    }

    @Override
    public void deleteSubject(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Subject does not exist with id: " + id
                        )
                );
        subjectRepository.deleteById(id);
    }


}
