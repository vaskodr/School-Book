package com.nbu.schoolbook.program;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "program")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProgramEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private ClassEntity associatedClass;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClassSessionEntity> classSessions;



}

