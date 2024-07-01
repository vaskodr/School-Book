package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByUserEntityIdAndStudentClassIdAndStudentClassSchoolId(String userId, Long classId, Long schoolId);
    List<StudentEntity> findAllByStudentClassSchoolIdAndStudentClassId(Long schoolId, Long classId);
    Optional<StudentEntity> findByIdAndStudentClassSchoolIdAndStudentClassId(Long studentId, Long schoolId, Long classId);
    boolean existsByIdAndStudentClassSchoolIdAndStudentClassId(Long studentId, Long schoolId, Long classId);
    Optional<StudentEntity> findByUserEntityId(String userId);
    boolean existsByUserEntity(UserEntity userEntity);
    @Query("SELECT s FROM StudentEntity s WHERE s.id = :studentId AND s.studentClass.id = :classId AND s.studentClass.school.id = :schoolId")
    Optional<StudentEntity> findByStudentIdAndClassIdAndSchoolId(
            @Param("studentId") Long studentId,
            @Param("classId") Long classId,
            @Param("schoolId") Long schoolId
    );
}
