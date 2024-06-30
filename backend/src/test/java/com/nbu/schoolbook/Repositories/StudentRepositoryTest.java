package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    private StudentEntity student;
    private ClassEntity studentClass;
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

        studentClass = new ClassEntity();
        studentClass.setId(1L);
        studentClass.setName("Class 1");
        studentClass.setSchool(school);

        student = new StudentEntity();
        student.setUserEntity(user);  // Associate saved UserEntity
        student.setStudentClass(studentClass);

        studentRepository.save(student);
    }

    @Test
    void findByUserEntityIdAndStudentClassIdAndStudentClassSchoolId_ShouldReturnStudent_WhenExists() {
        Optional<StudentEntity> foundStudent = studentRepository.findByUserEntityIdAndStudentClassIdAndStudentClassSchoolId(
                user.getId(), studentClass.getId(), school.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getUserEntity().getId()).isEqualTo(user.getId());
    }

    @Test
    void findAllByStudentClassSchoolIdAndStudentClassId_ShouldReturnStudents_WhenExists() {
        List<StudentEntity> students = studentRepository.findAllByStudentClassSchoolIdAndStudentClassId(school.getId(), studentClass.getId());

        assertThat(students).isNotEmpty();
        assertThat(students.get(0).getStudentClass().getId()).isEqualTo(studentClass.getId());
        assertThat(students.get(0).getStudentClass().getSchool().getId()).isEqualTo(school.getId());
    }

    @Test
    void findByIdAndStudentClassSchoolIdAndStudentClassId_ShouldReturnStudent_WhenExists() {
        Optional<StudentEntity> foundStudent = studentRepository.findByIdAndStudentClassSchoolIdAndStudentClassId(
                student.getId(), school.getId(), studentClass.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getId()).isEqualTo(student.getId());
    }

    @Test
    void existsByIdAndStudentClassSchoolIdAndStudentClassId_ShouldReturnTrue_WhenExists() {
        boolean exists = studentRepository.existsByIdAndStudentClassSchoolIdAndStudentClassId(
                student.getId(), school.getId(), studentClass.getId());

        assertThat(exists).isTrue();
    }

    @Test
    void findByUserEntityId_ShouldReturnStudent_WhenExists() {
        Optional<StudentEntity> foundStudent = studentRepository.findByUserEntityId(user.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getUserEntity().getId()).isEqualTo(user.getId());
    }

    @Test
    void existsByUserEntity_ShouldReturnTrue_WhenExists() {
        boolean exists = studentRepository.existsByUserEntity(user);

        assertThat(exists).isTrue();
    }

    @Test
    void findByStudentIdAndClassIdAndSchoolId_ShouldReturnStudent_WhenExists() {
        Optional<StudentEntity> foundStudent = studentRepository.findByStudentIdAndClassIdAndSchoolId(
                student.getId(), studentClass.getId(), school.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getId()).isEqualTo(student.getId());
    }
}
