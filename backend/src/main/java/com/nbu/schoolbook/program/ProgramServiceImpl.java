package com.nbu.schoolbook.program;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionMapper;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.class_session.ClassSessionService;
import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.SessionDTO;
import com.nbu.schoolbook.class_session.dto.TeacherSessionDTO;
import com.nbu.schoolbook.enums.DayOfWeek;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.program.dto.*;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.*;

@AllArgsConstructor
@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final ClassRepository classRepository;
    private final ClassSessionService classSessionService;
    private final ModelMapper modelMapper;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ClassSessionMapper classSessionMapper;
    private final ClassSessionRepository classSessionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createProgram(Long schoolId, Long classId, CreateProgramDTO createProgramDTO) {
        ClassEntity classEntity = classRepository.findByIdAndSchoolId(classId, schoolId)
                .orElseThrow(() -> new RuntimeException("Class not found for the given school"));

        ProgramEntity programEntity = new ProgramEntity();
        programEntity.setAssociatedClass(classEntity);
        programEntity.setClassSessions(new HashSet<>());
        programEntity = programRepository.save(programEntity);

        for (CreateClassSessionDTO sessionDTO : createProgramDTO.getClassSessions()) {
            ClassSessionDTO classSessionDTO = classSessionService.createClassSession(sessionDTO, programEntity.getId());
            ClassSessionEntity classSessionEntity = classSessionMapper.mapToEntity(classSessionDTO);
            // Ensure the entity is managed
            classSessionEntity = classSessionRepository.save(classSessionEntity);
            programEntity.getClassSessions().add(classSessionEntity);
        }

        programEntity = programRepository.save(programEntity);
    }

    @Override
    public ProgramDTO getClassProgram(Long schoolId, Long classId) {
        Optional<ProgramEntity> optionalProgram = programRepository.findByAssociatedClassSchoolIdAndAssociatedClassId(schoolId, classId);

        if (optionalProgram.isEmpty()) {
            throw new RuntimeException("Program not found");
        }

        ProgramEntity programEntity = optionalProgram.get();

        List<ClassSessionDTO> classSessions = programEntity.getClassSessions().stream()
                .map(cs -> modelMapper.map(cs, ClassSessionDTO.class))
                .collect(Collectors.toList());

        return new ProgramDTO(programEntity.getId(), programEntity.getAssociatedClass().getId(), classSessions);
    }

    @Override
    public List<ProgramDTO> getAllProgramsBySchoolId(Long schoolId) {
        return programRepository.findAllByAssociatedClassSchoolId(schoolId).stream()
                .map(program -> modelMapper.map(program, ProgramDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDTO getClassProgramByStudentId(Long schoolId, Long studentId) {
        Optional<ProgramEntity> optionalProgram = programRepository.findByAssociatedClassSchoolIdAndAssociatedClassStudentsId(schoolId, studentId);

        if (optionalProgram.isEmpty()) {
            throw new RuntimeException("Program not found");
        }

        ProgramEntity programEntity = optionalProgram.get();

        List<ClassSessionDTO> classSessions = programEntity.getClassSessions().stream()
                .map(cs -> modelMapper.map(cs, ClassSessionDTO.class))
                .collect(Collectors.toList());

        return new ProgramDTO(programEntity.getId(), programEntity.getAssociatedClass().getId(), classSessions);
    }

    @Override
    @Transactional
    public void updateProgram(Long schoolId, Long classId, Long programId, UpdateProgramDTO updateProgramDTO) {
        ClassEntity classEntity = classRepository.findByIdAndSchoolId(classId, schoolId)
                .orElseThrow(() -> new RuntimeException("Class not found for the given school"));

        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found!"));

        programEntity.setAssociatedClass(classEntity);

        programEntity.getClassSessions().clear();
        programEntity = programRepository.save(programEntity);

        for (CreateClassSessionDTO sessionDTO : updateProgramDTO.getClassSessions()) {
            ClassSessionDTO classSessionDTO = classSessionService.createClassSession(sessionDTO, programEntity.getId());
            ClassSessionEntity classSessionEntity = classSessionMapper.mapToEntity(classSessionDTO);
            // Ensure the entity is managed
            classSessionEntity = classSessionRepository.save(classSessionEntity);
            programEntity.getClassSessions().add(classSessionEntity);
        }

        programRepository.save(programEntity);
    }

    @Override
    @Transactional
    public void deleteProgram(Long schoolId, Long classId, Long programId) {
        ProgramEntity programEntity = programRepository.findByIdAndAssociatedClassSchoolIdAndAssociatedClassId(programId, schoolId, classId)
                .orElseThrow(() -> new RuntimeException("Program not found for the given school and class"));
        programRepository.delete(programEntity);
    }

    @Override
    @Transactional
    public WeeklyProgramDTO getWeeklyProgramForStudent(Long schoolId, Long studentId) {
        ProgramEntity programEntity = programRepository.findByAssociatedClassSchoolIdAndAssociatedClassStudentsId(schoolId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        Map<DayOfWeek, List<ClassSessionEntity>> sessionsByDay = programEntity.getClassSessions().stream()
                .collect(Collectors.groupingBy(ClassSessionEntity::getDay));

        Map<DayOfWeek, List<SessionDTO>> sessionsByDayDTO = sessionsByDay.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(session -> new SessionDTO(
                                        session.getStartTime(),
                                        session.getEndTime(),
                                        new String(userRepository.getReferenceById(teacherRepository.getReferenceById(session.getTeacher().getId()).getUserEntity().getId()).getFirstName() + " " + userRepository.getReferenceById(teacherRepository.getReferenceById(session.getTeacher().getId()).getUserEntity().getId()).getLastName()),
                                        session.getSubject().getName()
                                ))
                                .collect(Collectors.toList())
                ));

        return new WeeklyProgramDTO(sessionsByDayDTO);
    }

    @Override
    public WeeklyTeacherProgramDTO getWeeklyProgramForTeacher(Long schoolId, Long teacherId) {
        TeacherEntity teacherEntity = teacherRepository.findByIdAndSchoolId(teacherId, schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found for school id: " + schoolId + " and teacher id: " + teacherId));

        Map<DayOfWeek, List<ClassSessionEntity>> sessionsByDay = teacherEntity.getClassSessions().stream()
                .collect(Collectors.groupingBy(ClassSessionEntity::getDay));

        Map<DayOfWeek, List<TeacherSessionDTO>> sessionsByDayDTO = sessionsByDay.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(session -> new TeacherSessionDTO(
                                        session.getStartTime(),
                                        session.getEndTime(),
                                        session.getSubject().getName(),
                                        session.getProgram().getAssociatedClass().getName(),
                                        session.getProgram().getAssociatedClass().getLevel()
                                ))
                                .collect(Collectors.toList())
                ));

        return new WeeklyTeacherProgramDTO(sessionsByDayDTO);
    }
}
