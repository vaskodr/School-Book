package com.nbu.schoolbook.parent;

import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parent")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ParentEntity extends UserEntity {

}
