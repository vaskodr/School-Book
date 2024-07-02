package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.student.dto.StudentInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    @Query("SELECT t FROM TeacherEntity t ORDER BY t.id ASC")
    List<TeacherEntity> findAllOrderedById();

    boolean existsByUserEntity(UserEntity user);

    Optional<TeacherEntity> findByUserEntityId(String userId);

    List<TeacherEntity> findBySchoolId(Long schoolId);

    Optional<TeacherEntity> findByIdAndSchoolId(Long teacherId, Long schoolId);

    @Query("SELECT t FROM TeacherEntity t WHERE t.school.id = :schoolId AND t NOT IN (SELECT c.mentor FROM ClassEntity c WHERE c.mentor IS NOT NULL)")
    List<TeacherEntity> findAvailableMentors(Long schoolId);
    @Query("SELECT t.subjects FROM TeacherEntity t WHERE t.id = :teacherId AND t.school.id = :schoolId")
    List<SubjectEntity> findSubjectsBySchoolIdAndTeacherId(Long schoolId, Long teacherId);
    List<TeacherEntity> findBySubjectsId(Long subjectId);
}
