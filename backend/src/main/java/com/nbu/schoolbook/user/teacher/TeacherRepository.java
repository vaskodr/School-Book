package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    @Query("SELECT t FROM TeacherEntity t ORDER BY t.id ASC")
    List<TeacherEntity> findAllOrderedById();

    boolean existsByUserEntity(UserEntity user);

    Optional<TeacherEntity> findByUserEntityId(String userId);
}
