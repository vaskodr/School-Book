package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.grade.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
}
