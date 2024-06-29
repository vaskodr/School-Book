package com.nbu.schoolbook.absence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AbsenceRepository extends JpaRepository<AbsenceEntity, Long> {

    @Query("SELECT COUNT(a) FROM AbsenceEntity a WHERE a.student.id = :studentId")
    long countByStudentId(Long studentId);
}
