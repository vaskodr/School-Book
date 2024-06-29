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
}
