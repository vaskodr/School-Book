package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.absence.AbsenceEntity;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.grade.GradeEntity;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassEntity studentClass;
}
