package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        user.setGender(Gender.MALE);
        user.setPhone("1234567890");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password");

        userRepository.save(user);
    }

    @Test
    void findByUsernameOrEmail_ShouldReturnUser_WhenUsernameExists() {
        Optional<UserEntity> foundUser = userRepository.findByUsernameOrEmail("johndoe", "nonexistent@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void findByUsernameOrEmail_ShouldReturnUser_WhenEmailExists() {
        Optional<UserEntity> foundUser = userRepository.findByUsernameOrEmail("nonexistent", "john.doe@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findByUsernameOrEmail_ShouldReturnEmpty_WhenNeitherExists() {
        Optional<UserEntity> foundUser = userRepository.findByUsernameOrEmail("nonexistent", "nonexistent@example.com");

        assertThat(foundUser).isNotPresent();
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUsernameExists() {
        boolean exists = userRepository.existsByUsername("johndoe");

        assertThat(exists).isTrue();
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenUsernameDoesNotExist() {
        boolean exists = userRepository.existsByUsername("nonexistent");

        assertThat(exists).isFalse();
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        boolean exists = userRepository.existsByEmail("john.doe@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenEmailDoesNotExist() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        assertThat(exists).isFalse();
    }
}
