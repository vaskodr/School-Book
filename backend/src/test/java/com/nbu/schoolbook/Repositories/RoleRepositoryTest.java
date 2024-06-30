package com.nbu.schoolbook.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private RoleEntity role;

    @BeforeEach
    @Rollback(false)
    void setUp() {
        role = new RoleEntity();
        role.setName("ROLE_USER");
        roleRepository.save(role);
    }

    @Test
    void testCreateRole() {
        RoleEntity newRole = new RoleEntity();
        newRole.setName("ROLE_ADMIN");

        RoleEntity savedRole = roleRepository.save(newRole);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void testFindRoleByName() {
        RoleEntity foundRole = roleRepository.findByName("ROLE_USER");

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void testUpdateRole() {
        role.setName("ROLE_SUPERUSER");

        RoleEntity updatedRole = roleRepository.save(role);

        assertThat(updatedRole.getName()).isEqualTo("ROLE_SUPERUSER");
    }

    @Test
    void testDeleteRole() {
        roleRepository.delete(role);

        Optional<RoleEntity> deletedRole = roleRepository.findById(role.getId());

        assertThat(deletedRole).isNotPresent();
    }

    @Test
    void testFindAllRoles() {
        Iterable<RoleEntity> roles = roleRepository.findAll();

        assertThat(roles).hasSizeGreaterThanOrEqualTo(1);
    }
}
