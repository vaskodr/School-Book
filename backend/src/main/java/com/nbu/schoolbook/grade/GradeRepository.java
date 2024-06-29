package com.nbu.schoolbook.grade;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    List<GradeEntity> findAllByStudentId(Long studentId);
}
