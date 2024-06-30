package com.nbu.schoolbook.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.director.DirectorEntity;
import com.nbu.schoolbook.user.director.DirectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    private DirectorEntity director;
    private UserEntity directorUser;
    private SchoolEntity school;

    @BeforeEach
    @Transactional
    @Rollback(false)
    void setUp() {
        // Create and save director user
        directorUser = new UserEntity();
        directorUser.setId("1234567890");
        directorUser.setFirstName("Alice");
        directorUser.setLastName("Smith");
        directorUser.setDateOfBirth(LocalDate.of(1980, 1, 1));
        directorUser.setGender(Gender.FEMALE);
        directorUser.setPhone("1234567890");
        directorUser.setEmail("alice.smith@example.com");
        directorUser.setUsername("alicesmith");
        directorUser.setPassword("password");
        userRepository.save(directorUser);

        // Create and save school entity
        school = new SchoolEntity();
        school.setName("Test School");
        school.setAddress("123 Test St"); // Set the address field
        schoolRepository.save(school);

        // Create and save director entity
        director = new DirectorEntity();
        director.setUserEntity(directorUser);
        director.setSchool(school);
        directorRepository.save(director);
    }

    @Test
    void existsByUserEntity_ShouldReturnTrue_WhenDirectorExists() {
        boolean exists = directorRepository.existsByUserEntity(directorUser);

        assertThat(exists).isTrue();
    }

    @Test
    void findByUserEntityId_ShouldReturnDirector_WhenExists() {
        DirectorEntity foundDirector = directorRepository.findByUserEntityId(directorUser.getId());

        assertThat(foundDirector).isNotNull();
        assertThat(foundDirector.getUserEntity().getId()).isEqualTo(directorUser.getId());
    }

    @Test
    void findByIdAndSchoolId_ShouldReturnDirector_WhenExists() {
        Optional<DirectorEntity> foundDirector = directorRepository.findByIdAndSchoolId(director.getId(), school.getId());

        assertThat(foundDirector).isPresent();
        assertThat(foundDirector.get().getId()).isEqualTo(director.getId());
    }

    @Test
    void existsByIdAndSchoolId_ShouldReturnTrue_WhenDirectorExists() {
        boolean exists = directorRepository.existsByIdAndSchoolId(director.getId(), school.getId());

        assertThat(exists).isTrue();
    }

    @Test
    void existsByUserEntity_ShouldReturnFalse_WhenDirectorDoesNotExist() {
        UserEntity newUser = new UserEntity();
        newUser.setId("1111111111");
        newUser.setFirstName("Non");
        newUser.setLastName("Existing");
        newUser.setDateOfBirth(LocalDate.of(2000, 1, 1));
        newUser.setGender(Gender.MALE);
        newUser.setPhone("1111111111");
        newUser.setEmail("non.existing@example.com");
        newUser.setUsername("nonexisting");
        newUser.setPassword("password");

        boolean exists = directorRepository.existsByUserEntity(newUser);

        assertThat(exists).isFalse();
    }

    @Test
    void findByUserEntityId_ShouldReturnNull_WhenDirectorDoesNotExist() {
        DirectorEntity foundDirector = directorRepository.findByUserEntityId("1111111111");

        assertThat(foundDirector).isNull();
    }

    @Test
    void findByIdAndSchoolId_ShouldReturnEmpty_WhenDirectorDoesNotExist() {
        Optional<DirectorEntity> foundDirector = directorRepository.findByIdAndSchoolId(999L, 999L);

        assertThat(foundDirector).isNotPresent();
    }

    @Test
    void existsByIdAndSchoolId_ShouldReturnFalse_WhenDirectorDoesNotExist() {
        boolean exists = directorRepository.existsByIdAndSchoolId(999L, 999L);

        assertThat(exists).isFalse();
    }
}
