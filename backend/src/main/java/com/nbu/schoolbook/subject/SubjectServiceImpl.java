package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import com.nbu.schoolbook.subject.dto.UpdateSubjectDTO;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherMapper;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
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
    private final TeacherMapper teacherMapper;

    @Override
    public void createSubject(CreateSubjectDTO createSubjectDTO) {
        SubjectEntity subject = subjectMapper.mapCreateDTOToEntity(createSubjectDTO);
        subject.setClassSessions(new HashSet<>());
        subjectRepository.save(subject);

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
    public List<TeacherDTO> getTeachersBySubjectId(Long subjectId) {
        List<TeacherEntity> teacherEntities = teacherRepository.findBySubjectsId(subjectId);
        return teacherEntities.stream()
                .map(teacherMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateSubject(Long id, UpdateSubjectDTO updateSubjectDTO) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject does not exists with id: " + id));

        if (updateSubjectDTO.getName() != null) {
            subject.setName(updateSubjectDTO.getName());
        }

        subjectRepository.save(subject);
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
