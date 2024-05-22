package com.nbu.schoolbook.role;

import com.nbu.schoolbook.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
