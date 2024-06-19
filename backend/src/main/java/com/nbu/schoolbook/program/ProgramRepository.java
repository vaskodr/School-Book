package com.nbu.schoolbook.program;

import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
}
