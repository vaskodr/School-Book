package com.nbu.schoolbook.program;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionMapper;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.class_session.ClassSessionService;
import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
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
    public ProgramDTO createProgram(Long schoolId, Long classId, CreateProgramDTO createProgramDTO) {
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
        return programMapper.mapToDTO(programEntity);
    }

    @Override
    public ProgramDTO getProgramById(Long id) {
        ProgramEntity program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found!"));
        return programMapper.mapToDTO(program);
    }

    @Override
    public List<ProgramDTO> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(programMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDTO getClassProgram(Long schoolId, Long classId) {
        Optional<ProgramEntity> optionalProgram = programRepository.findBySchoolIdAndClassId(schoolId, classId);

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
    public ProgramDTO getClassProgramByStudentId(Long schoolId, Long studentId) {
        Optional<ProgramEntity> optionalProgram = programRepository.findBySchoolIdAndStudentId(schoolId, studentId);

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
        List<ClassEntity> classes = classRepository.findClassesBySchoolId(schoolId);
        return programRepository.findAllByAssociatedClassIn(classes).stream()
                .map(program -> modelMapper.map(program, ProgramDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ProgramDTO updateProgram(Long id, UpdateProgramDTO updateProgramDTO) {
        ProgramEntity program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found!"));

        program.setAssociatedClass(classRepository.findById(updateProgramDTO.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found!")));
        programRepository.save(program);

        updateProgramDTO.getClassSessions().forEach(sessionDTO -> {
            classSessionService.updateClassSession(sessionDTO.getId(), sessionDTO);
        });

        return programMapper.mapToDTO(program);
    }

    @Override
    @Transactional
    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }
}
