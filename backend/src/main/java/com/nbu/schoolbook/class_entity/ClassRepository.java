package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.school.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findBySchool(SchoolEntity school);

    List<ClassEntity> findClassesBySchoolId(Long schoolId);

    Optional<ClassEntity> findByIdAndSchoolId(Long classId, Long schoolId);

    List<ClassEntity> findBySchoolId(Long schoolId);
}
