package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.user.director.DirectorEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToOne(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private DirectorEntity director;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ClassEntity> classes;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherEntity> teachers;




}
