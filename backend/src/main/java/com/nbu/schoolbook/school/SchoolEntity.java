package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.user.director.DirectorEntity;
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

    @OneToOne(mappedBy = "school")
    private DirectorEntity director;

    @OneToMany(mappedBy = "school")
    private Set<ClassEntity> classes;

    @OneToMany(mappedBy = "school")
    private Set<TeacherEntity> teachers;

    public SchoolEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public SchoolEntity(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
