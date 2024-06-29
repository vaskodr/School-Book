package com.nbu.schoolbook.absence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<AbsenceEntity, Long> {
    List<AbsenceEntity> findAllByStudentId(Long studentId);
}
