package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.dto.ProgramDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgramMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ProgramMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProgramDTO mapToDTO(ProgramEntity program) {
        return modelMapper.map(program, ProgramDTO.class);
    }

    public ProgramEntity mapToEntity(ProgramDTO programDTO) {
        return modelMapper.map(programDTO, ProgramEntity.class);
    }
}
