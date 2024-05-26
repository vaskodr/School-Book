package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TeacherEntity extends UserEntity {

    @ManyToOne
    private SchoolEntity school;

    @ManyToMany
    @JoinTable(name = "teacher_has_qualification",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<SubjectEntity> subjects;
}
