package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.student.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
