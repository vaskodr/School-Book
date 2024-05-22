package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.school.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
}
