package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
}
