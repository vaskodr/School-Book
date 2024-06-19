package com.nbu.schoolbook.role;

import com.nbu.schoolbook.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
}
