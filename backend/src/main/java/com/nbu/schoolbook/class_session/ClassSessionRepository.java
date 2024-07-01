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
    Optional<ClassSessionEntity> findByDayAndStartTimeAndEndTimeAndTeacherAndSubjectAndProgram(DayOfWeek day, String startTime, String endTime, TeacherEntity teacher, SubjectEntity subject, ProgramEntity program);

    boolean existsByTeacherIdAndSubjectIdAndProgram_AssociatedClass_IdAndProgram_AssociatedClass_School_Id(Long teacherId, Long subjectId, Long classId, Long schoolId);
}
