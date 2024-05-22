package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassSessionRepository extends JpaRepository<ClassSessionEntity, Long> {
}
