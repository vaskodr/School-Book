package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parent")
@Getter
@Setter
public class ParentEntity extends UserEntity {

}
