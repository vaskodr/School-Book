package com.nbu.schoolbook.program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public ProgramEntity saveProgram(ProgramEntity program) {
        return programRepository.save(program);
    }

    @Override
    public ProgramEntity updateProgram(long id, ProgramEntity program) {
        Optional<ProgramEntity> existingProgram = programRepository.findById(id);
        if (existingProgram.isPresent()) {
            ProgramEntity updatedProgram = existingProgram.get();
            updatedProgram.setAssociatedClass(program.getAssociatedClass());
            updatedProgram.setClassSessions(program.getClassSessions());
            return programRepository.save(updatedProgram);
        } else {
            throw new RuntimeException("Program not found with id: " + id);
        }
    }

    @Override
    public void deleteProgram(long id) {
        programRepository.deleteById(id);
    }

    @Override
    public ProgramEntity getProgramById(long id) {
        return programRepository.findById(id).orElseThrow(() -> new RuntimeException("Program not found with id: " + id));
    }

    @Override
    public List<ProgramEntity> getAllPrograms() {
        return programRepository.findAll();
    }
}
