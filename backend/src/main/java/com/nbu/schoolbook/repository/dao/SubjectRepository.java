package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.subject.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
}
