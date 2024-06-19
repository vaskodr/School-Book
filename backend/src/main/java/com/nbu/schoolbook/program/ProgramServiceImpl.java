package com.nbu.schoolbook.program;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final ClassSessionRepository classSessionRepository;
    private final ClassRepository classRepository;

    @Override
    public ProgramDTO createProgram(CreateProgramDTO createProgramDTO) {
        ProgramEntity programEntity = new ProgramEntity();

        ClassEntity associatedClass = classRepository.findById(createProgramDTO.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        programEntity.setAssociatedClass(associatedClass);

        Set<ClassSessionEntity> classSessions = createProgramDTO.getClassSessionIds().stream()
                .map(id -> classSessionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Class session not found")))
                .collect(Collectors.toSet());
        programEntity.setClassSessions(classSessions);

        ProgramEntity savedProgram = programRepository.save(programEntity);
        return programMapper.mapToDTO(savedProgram);
    }

    @Override
    public ProgramDTO getProgramById(Long id) {
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));
        return programMapper.mapToDTO(programEntity);
    }

    @Override
    public List<ProgramDTO> getAllPrograms() {
        List<ProgramEntity> programs = programRepository.findAll();
        return programs.stream()
                .map(programMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDTO updateProgram(Long id, UpdateProgramDTO updateProgramDTO) {
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        ClassEntity associatedClass = classRepository.findById(updateProgramDTO.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        programEntity.setAssociatedClass(associatedClass);

        Set<ClassSessionEntity> classSessions = updateProgramDTO.getClassSessionIds().stream()
                .map(sessionId -> classSessionRepository.findById(sessionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Class session not found")))
                .collect(Collectors.toSet());
        programEntity.setClassSessions(classSessions);

        ProgramEntity updatedProgram = programRepository.save(programEntity);
        return programMapper.mapToDTO(updatedProgram);
    }

    @Override
    public void deleteProgram(Long id) {
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Program not found");
        }
        programRepository.deleteById(id);
    }
}
