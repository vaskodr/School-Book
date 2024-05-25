package com.nbu.schoolbook.absence;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "absence")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AbsenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne
    private ClassSessionEntity classSession;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private StudentEntity student;

}
