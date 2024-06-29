package com.nbu.schoolbook.program;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionMapper;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.class_session.ClassSessionService;
import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.ProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
}
