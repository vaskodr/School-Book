package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    @Query("SELECT p FROM ParentEntity p JOIN p.students s WHERE s.id = :studentId")
    List<ParentEntity> findParentsByStudentId(Long studentId);

    Optional<ParentEntity> findByUserEntity(UserEntity parentUser);

    ParentEntity findByUserEntityId(String userId);
}
