package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.class_entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}
