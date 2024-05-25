package com.nbu.schoolbook.school;

import com.nbu.schoolbook.school.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
}
