package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "parent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParentEntity extends UserEntity {

    @ManyToMany(mappedBy = "parents")
    private Set<StudentEntity> students;

}
