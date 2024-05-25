package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.enums.DayOfWeek;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "school_session")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClassSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private DayOfWeek day;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @OneToOne
    @JoinColumn(nullable = false)
    private TeacherEntity teacher;

    @OneToOne
    @JoinColumn(nullable = false)
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProgramEntity program;

}
