package com.nbu.schoolbook.program;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.UpdateProgramDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
    Optional<Object> findAllByAssociatedClassIn(List<ClassEntity> classes);
    @Query("SELECT p FROM ProgramEntity p JOIN p.associatedClass c WHERE c.school.id = :schoolId AND c.id = :classId")
    Optional<ProgramEntity> findBySchoolIdAndClassId(@Param("schoolId") Long schoolId, @Param("classId") Long classId);

    @Query("SELECT p FROM ProgramEntity p JOIN p.associatedClass c JOIN c.students s WHERE s.id = :studentId AND c.school.id = :schoolId")
    Optional<ProgramEntity> findBySchoolIdAndStudentId(@Param("schoolId") Long schoolId, @Param("studentId") Long studentId);
}
