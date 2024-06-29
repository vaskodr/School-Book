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
    Optional<ProgramEntity> findByAssociatedClassSchoolIdAndAssociatedClassId(Long schoolId, Long classId);
    List<ProgramEntity> findAllByAssociatedClassSchoolId(Long schoolId);
    List<ProgramEntity> findAllByAssociatedClass(ClassEntity classEntity);
    Optional<ProgramEntity> findByAssociatedClassSchoolIdAndAssociatedClassStudentsId(Long schoolId, Long studentId);
    Optional<ProgramEntity> findByIdAndAssociatedClassSchoolIdAndAssociatedClassId(Long programId, Long schoolId, Long classId);
}
