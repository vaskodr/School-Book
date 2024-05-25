package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.absence.AbsenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<AbsenceEntity, Long> {
}
