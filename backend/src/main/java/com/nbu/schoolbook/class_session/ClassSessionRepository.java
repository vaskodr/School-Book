package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.enums.DayOfWeek;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface ClassSessionRepository extends JpaRepository<ClassSessionEntity, Long> {
}
