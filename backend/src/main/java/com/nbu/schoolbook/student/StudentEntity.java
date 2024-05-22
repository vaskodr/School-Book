package com.nbu.schoolbook.student;

import com.nbu.schoolbook.absence.AbsenceEntity;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.grade.GradeEntity;
import com.nbu.schoolbook.parent.ParentEntity;
import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudentEntity extends UserEntity {

    @OneToMany(mappedBy = "student")
    private Set<GradeEntity> grades;

    @OneToMany(mappedBy = "student")
    private Set<AbsenceEntity> absences;

    @ManyToMany
    @JoinTable(name = "students_have_parents",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private Set<ParentEntity> parents;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity studentClass;
}