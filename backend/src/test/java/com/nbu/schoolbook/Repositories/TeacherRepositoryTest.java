package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    private TeacherEntity teacher;
    private SchoolEntity school;
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

        userRepository.save(user);  // Save UserEntity first

        school = new SchoolEntity();
        school.setId(1L);
        school.setName("Test School");

        teacher = new TeacherEntity();
        teacher.setUserEntity(user);  // Associate saved UserEntity
        teacher.setSchool(school);

        teacherRepository.save(teacher);
    }

    @Test
    void findAllOrderedById_ShouldReturnTeachersOrderedById() {
        List<TeacherEntity> teachers = teacherRepository.findAllOrderedById();

        assertThat(teachers).isNotEmpty();
        assertThat(teachers.get(0).getUserEntity().getId()).isEqualTo(user.getId());
    }

    @Test
    void existsByUserEntity_ShouldReturnTrue_WhenUserExists() {
        boolean exists = teacherRepository.existsByUserEntity(user);

        assertThat(exists).isTrue();
    }

    @Test
    void existsByUserEntity_ShouldReturnFalse_WhenUserDoesNotExist() {
        UserEntity newUser = new UserEntity();
        newUser.setId("0987654321");
        newUser.setFirstName("Jane");
        newUser.setLastName("Doe");
        newUser.setDateOfBirth(LocalDate.of(1992, 2, 2));
        newUser.setGender(Gender.FEMALE);
        newUser.setPhone("0987654321");
        newUser.setEmail("jane.doe@example.com");
        newUser.setUsername("janedoe");
        newUser.setPassword("password");

        boolean exists = teacherRepository.existsByUserEntity(newUser);

        assertThat(exists).isFalse();
    }

    @Test
    void findByUserEntityId_ShouldReturnTeacher_WhenUserIdExists() {
        Optional<TeacherEntity> foundTeacher = teacherRepository.findByUserEntityId(user.getId());

        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getUserEntity().getId()).isEqualTo(user.getId());
    }

    @Test
    void findByUserEntityId_ShouldReturnEmpty_WhenUserIdDoesNotExist() {
        Optional<TeacherEntity> foundTeacher = teacherRepository.findByUserEntityId("nonexistent");

        assertThat(foundTeacher).isNotPresent();
    }

    @Test
    void findBySchoolId_ShouldReturnTeachers_WhenSchoolIdExists() {
        List<TeacherEntity> teachers = teacherRepository.findBySchoolId(school.getId());

        assertThat(teachers).isNotEmpty();
        assertThat(teachers.get(0).getSchool().getId()).isEqualTo(school.getId());
    }

    @Test
    void findBySchoolId_ShouldReturnEmptyList_WhenSchoolIdDoesNotExist() {
        List<TeacherEntity> teachers = teacherRepository.findBySchoolId(999L);

        assertThat(teachers).isEmpty();
    }

    @Test
    void findByIdAndSchoolId_ShouldReturnTeacher_WhenTeacherAndSchoolIdExist() {
        Optional<TeacherEntity> foundTeacher = teacherRepository.findByIdAndSchoolId(teacher.getId(), school.getId());

        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getUserEntity().getId()).isEqualTo(user.getId());
        assertThat(foundTeacher.get().getSchool().getId()).isEqualTo(school.getId());
    }

    @Test
    void findByIdAndSchoolId_ShouldReturnEmpty_WhenTeacherOrSchoolIdDoNotExist() {
        Optional<TeacherEntity> foundTeacher = teacherRepository.findByIdAndSchoolId(teacher.getId(), 999L);

        assertThat(foundTeacher).isNotPresent();
    }
}
