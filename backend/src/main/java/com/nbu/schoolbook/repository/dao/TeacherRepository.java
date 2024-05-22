package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.teacher.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
