package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}
