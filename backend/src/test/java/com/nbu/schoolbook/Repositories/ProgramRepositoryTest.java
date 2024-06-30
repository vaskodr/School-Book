package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.enums.ClassLevel;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.program.ProgramRepository;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    private ProgramEntity program;
    private SchoolEntity school;
    private ClassEntity classEntity;
    private StudentEntity student;

    @BeforeEach
    @Rollback(false)
    void setUp() {
        // Create and save a school entity
        school = new SchoolEntity();
        school.setName("Test School");
        school.setAddress("123 Test St");
        school = schoolRepository.save(school);

        // Create and save a class entity
        classEntity = new ClassEntity();
        classEntity.setName("Class 1");
        classEntity.setLevel(ClassLevel.FIRST);
        classEntity.setSchool(school);
        classEntity = classRepository.save(classEntity);

        // Create and save a user entity
        UserEntity user = new UserEntity();
        user.setId("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        user.setGender(Gender.MALE);
        user.setPhone("1234567890");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password");
        user = userRepository.save(user);

        // Create and save a student entity
        student = new StudentEntity();
        student.setUserEntity(user);
        student.setStudentClass(classEntity);
        student = studentRepository.save(student);

        // Create and save a program entity
        program = new ProgramEntity();
        program.setAssociatedClass(classEntity);
        program.setClassSessions(new HashSet<>());
        program = programRepository.save(program);
    }

    @Test
    void testFindByAssociatedClassSchoolIdAndAssociatedClassId() {
        Optional<ProgramEntity> foundProgram = programRepository.findByAssociatedClassSchoolIdAndAssociatedClassId(
                school.getId(), classEntity.getId());

        assertThat(foundProgram).isPresent();
        assertThat(foundProgram.get().getAssociatedClass().getId()).isEqualTo(classEntity.getId());
    }

    @Test
    void testFindAllByAssociatedClassSchoolId() {
        List<ProgramEntity> programs = programRepository.findAllByAssociatedClassSchoolId(school.getId());

        assertThat(programs).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void testFindAllByAssociatedClass() {
        List<ProgramEntity> programs = programRepository.findAllByAssociatedClass(classEntity);

        assertThat(programs).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void testFindByAssociatedClassSchoolIdAndAssociatedClassStudentsId() {
        Optional<ProgramEntity> foundProgram = programRepository.findByAssociatedClassSchoolIdAndAssociatedClassStudentsId(
                school.getId(), student.getId());

        assertThat(foundProgram).isPresent();
    }

    @Test
    void testFindByIdAndAssociatedClassSchoolIdAndAssociatedClassId() {
        Optional<ProgramEntity> foundProgram = programRepository.findByIdAndAssociatedClassSchoolIdAndAssociatedClassId(
                program.getId(), school.getId(), classEntity.getId());

        assertThat(foundProgram).isPresent();
    }

    @Test
    void testGetProgramEntityById() {
        Optional<ProgramEntity> foundProgram = programRepository.getProgramEntityById(program.getId());

        assertThat(foundProgram).isPresent();
    }
}
