package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.program.ProgramRepository;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClassSessionServiceImpl implements ClassSessionService{

    private final ProgramRepository programRepository;
    private final ClassSessionMapper classSessionMapper;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ClassSessionRepository classSessionRepository;


    @Override
    @Transactional
    public ClassSessionDTO createClassSession(CreateClassSessionDTO createClassSessionDTO, Long programId) {
        TeacherEntity teacher = teacherRepository.findById(createClassSessionDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        SubjectEntity subject = subjectRepository.findById(createClassSessionDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        ProgramEntity program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        if (teacher.getSubjects().contains(subject)) {
            ClassSessionEntity classSessionEntity = new ClassSessionEntity();
            classSessionEntity.setDay(createClassSessionDTO.getDay());
            classSessionEntity.setStartTime(createClassSessionDTO.getStartTime());
            classSessionEntity.setEndTime(createClassSessionDTO.getEndTime());
            classSessionEntity.setTeacher(teacher);
            classSessionEntity.setSubject(subject);
            classSessionEntity.setProgram(program);

            teacher.getClassSessions().add(classSessionEntity);
            subject.getClassSessions().add(classSessionEntity);

            teacherRepository.save(teacher);
            subjectRepository.save(subject);
            // Save the class session entity
            classSessionEntity = classSessionRepository.save(classSessionEntity);
            return classSessionMapper.mapToDTO(classSessionEntity);
        } else {
            throw new RuntimeException("That teacher does not have qualification for that subject!");
        }

    }

    @Override
    public ClassSessionDTO getClassSessionById(Long id) {
        ClassSessionEntity classSession = classSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassSession not found!"));
        return classSessionMapper.mapToDTO(classSession);
    }

    @Override
    public List<ClassSessionDTO> getAllClassSessions() {
        return classSessionRepository.findAll().stream()
                .map(classSessionMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassSessionDTO updateClassSession(Long id, UpdateClassSessionDTO updateClassSessionDTO) {
        ClassSessionEntity classSession = classSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassSession not found!"));

        classSession.setDay(updateClassSessionDTO.getDay());
        classSession.setStartTime(updateClassSessionDTO.getStartTime());
        classSession.setEndTime(updateClassSessionDTO.getEndTime());
        classSession.setTeacher(teacherRepository.findById(updateClassSessionDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found!")));
        classSession.setSubject(subjectRepository.findById(updateClassSessionDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found!")));

        classSession = classSessionRepository.save(classSession);
        return classSessionMapper.mapToDTO(classSession);
    }

    @Override
    public void deleteClassSession(Long id) {
        classSessionRepository.deleteById(id);
    }
}
