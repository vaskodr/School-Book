package com.nbu.schoolbook.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.parent.ParentRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParentRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    private ParentEntity parent;
    private StudentEntity student;
    private UserEntity parentUser;
    private UserEntity studentUser;

    @BeforeEach
    @Transactional
    @Rollback(false)
    void setUp() {
        // Create and save parent user
        parentUser = new UserEntity();
        parentUser.setId("1234567890");
        parentUser.setFirstName("John");
        parentUser.setLastName("Doe");
        parentUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
        parentUser.setGender(Gender.MALE);
        parentUser.setPhone("1234567890");
        parentUser.setEmail("john.doe@example.com");
        parentUser.setUsername("johndoe");
        parentUser.setPassword("password");
        userRepository.save(parentUser);

        // Create and save student user
        studentUser = new UserEntity();
        studentUser.setId("0987654321");
        studentUser.setFirstName("Jane");
        studentUser.setLastName("Doe");
        studentUser.setDateOfBirth(LocalDate.of(2005, 5, 5));
        studentUser.setGender(Gender.FEMALE);
        studentUser.setPhone("0987654321");
        studentUser.setEmail("jane.doe@example.com");
        studentUser.setUsername("janedoe");
        studentUser.setPassword("password");
        userRepository.save(studentUser);

        // Create and save student entity
        student = new StudentEntity();
        student.setUserEntity(studentUser);
        studentRepository.save(student);

        // Create and save parent entity
        parent = new ParentEntity();
        parent.setUserEntity(parentUser);
        Set<StudentEntity> students = new HashSet<>();
        students.add(student);
        parent.setStudents(students);
        parentRepository.save(parent);

        // Ensure the student entity is updated with the parent relationship
        Set<ParentEntity> parents = new HashSet<>();
        parents.add(parent);
        student.setParents(parents);
        studentRepository.save(student);
    }

    @Test
    void findParentsByStudentId_ShouldReturnParents_WhenExists() {
        System.out.println("DEBUG: Parent ID: " + parent.getId());
        System.out.println("DEBUG: Student ID: " + student.getId());

        parentRepository.flush();
        studentRepository.flush();

        List<ParentEntity> foundParents = parentRepository.findParentsByStudentId(student.getId());

        System.out.println("DEBUG: Found Parents: " + foundParents.size());

        assertThat(foundParents).isNotEmpty();
        assertThat(foundParents.get(0).getUserEntity().getId()).isEqualTo(parentUser.getId());
    }

    @Test
    void findByUserEntity_ShouldReturnParent_WhenExists() {
        Optional<ParentEntity> foundParent = parentRepository.findByUserEntity(parentUser);

        assertThat(foundParent).isPresent();
        assertThat(foundParent.get().getUserEntity().getId()).isEqualTo(parentUser.getId());
    }

    @Test
    void findByUserEntityId_ShouldReturnParent_WhenExists() {
        ParentEntity foundParent = parentRepository.findByUserEntityId(parentUser.getId());

        assertThat(foundParent).isNotNull();
        assertThat(foundParent.getUserEntity().getId()).isEqualTo(parentUser.getId());
    }

    @Test
    void findParentsByStudentId_ShouldReturnEmptyList_WhenNoParentExists() {
        List<ParentEntity> foundParents = parentRepository.findParentsByStudentId(999L);

        assertThat(foundParents).isEmpty();
    }

    @Test
    void findByUserEntity_ShouldReturnEmpty_WhenParentDoesNotExist() {
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

        Optional<ParentEntity> foundParent = parentRepository.findByUserEntity(newUser);

        assertThat(foundParent).isNotPresent();
    }

    @Test
    void findByUserEntityId_ShouldReturnNull_WhenParentDoesNotExist() {
        ParentEntity foundParent = parentRepository.findByUserEntityId("1111111111");

        assertThat(foundParent).isNull();
    }
}
