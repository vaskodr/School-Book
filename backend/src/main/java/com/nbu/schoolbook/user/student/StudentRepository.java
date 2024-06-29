package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    boolean existsByUserEntity(UserEntity existingUser);

    Optional<StudentEntity> findByUserEntityId(String userId);
}
