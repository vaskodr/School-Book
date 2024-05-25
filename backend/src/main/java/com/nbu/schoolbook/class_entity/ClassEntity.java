package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.enums.ClassLevel;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassLevel level;

    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
    private SchoolEntity school;

    @OneToOne
    @JoinColumn(name = "mentor", nullable = false)
    private TeacherEntity mentor;
}
