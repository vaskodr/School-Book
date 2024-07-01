package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.enums.DayOfWeek;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;

@Entity
@Table(name = "class_session")
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
    //@Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private ProgramEntity program;

}
