package com.nbu.schoolbook.user.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    @Query("SELECT t FROM TeacherEntity t ORDER BY t.id ASC")
    List<TeacherEntity> findAllOrderedById();
}
