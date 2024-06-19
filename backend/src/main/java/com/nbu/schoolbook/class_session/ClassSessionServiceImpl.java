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

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClassSessionServiceImpl implements ClassSessionService{

    private final ClassSessionRepository classSessionRepository;
    private final ClassSessionMapper classSessionMapper;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ProgramRepository programRepository;



    @Override
    public ClassSessionDTO createClassSession(CreateClassSessionDTO createClassSessionDTO) {
        ClassSessionEntity classSessionEntity = new ClassSessionEntity();

        classSessionEntity.setDay(createClassSessionDTO.getDay());
        classSessionEntity.setStartTime(createClassSessionDTO.getStartTime());
        classSessionEntity.setEndTime(createClassSessionDTO.getEndTime());

        TeacherEntity teacher = teacherRepository.findById(createClassSessionDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        classSessionEntity.setTeacher(teacher);

        SubjectEntity subject = subjectRepository.findById(createClassSessionDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        classSessionEntity.setSubject(subject);

        ProgramEntity program = programRepository.findById(createClassSessionDTO.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));
        classSessionEntity.setProgram(program);

        ClassSessionEntity savedClassSession = classSessionRepository.save(classSessionEntity);
        return classSessionMapper.mapToDTO(savedClassSession);
    }

    @Override
    public ClassSessionDTO getClassSessionById(Long id) {
        ClassSessionEntity classSessionEntity = classSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found"));
        return classSessionMapper.mapToDTO(classSessionEntity);
    }

    @Override
    public List<ClassSessionDTO> getAllClassSessions() {
        List<ClassSessionEntity> classSessions = classSessionRepository.findAll();
        return classSessions.stream()
                .map(classSession -> classSessionMapper.mapToDTO(classSession))
                .collect(Collectors.toList());
    }

    @Override
    public ClassSessionDTO updateClassSession(Long id, UpdateClassSessionDTO updateClassSessionDTO) {
        ClassSessionEntity classSessionEntity = classSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found"));

        classSessionEntity.setDay(updateClassSessionDTO.getDay());
        classSessionEntity.setStartTime(updateClassSessionDTO.getStartTime());
        classSessionEntity.setEndTime(updateClassSessionDTO.getEndTime());

        TeacherEntity teacher = teacherRepository.findById(updateClassSessionDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        classSessionEntity.setTeacher(teacher);

        SubjectEntity subject = subjectRepository.findById(updateClassSessionDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        classSessionEntity.setSubject(subject);

        ProgramEntity program = programRepository.findById(updateClassSessionDTO.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));
        classSessionEntity.setProgram(program);

        ClassSessionEntity updatedClassSession = classSessionRepository.save(classSessionEntity);
        return classSessionMapper.mapToDTO(updatedClassSession);
    }

    @Override
    public void deleteClassSession(Long id) {
        if (!classSessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Class session not found");
        }
        classSessionRepository.deleteById(id);
    }
}
