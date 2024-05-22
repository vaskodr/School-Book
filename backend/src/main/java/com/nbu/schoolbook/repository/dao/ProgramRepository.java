package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.program.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
}
