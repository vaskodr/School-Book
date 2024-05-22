package com.nbu.schoolbook.director;

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
public class DirectorEntity extends UserEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private SchoolEntity school;

}
