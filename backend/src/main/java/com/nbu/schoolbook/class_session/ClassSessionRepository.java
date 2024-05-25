package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassSessionRepository extends JpaRepository<ClassSessionEntity, Long> {
}
