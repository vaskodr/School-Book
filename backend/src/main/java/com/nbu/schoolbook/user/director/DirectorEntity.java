package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "director")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DirectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne
    private SchoolEntity school;

}
