package com.nbu.schoolbook.grade;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "grade")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal grade;
    @Column(nullable = false)
    private LocalDate date;

    @OneToOne
    private ClassSessionEntity classSession;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private StudentEntity student;


}
