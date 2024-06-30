package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.enums.ClassLevel;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClassRepositoryTest {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private SchoolEntity school;
    private TeacherEntity teacher;

    @BeforeEach
    @Rollback(false)
    void setUp() {
        // Create and save a school entity
        school = new SchoolEntity();
        school.setName("Test School");
        school.setAddress("123 Test St");
        school = schoolRepository.save(school);

        // Create and save a user entity for teacher
        UserEntity teacherUser = new UserEntity();
        teacherUser.setId("0987654321");
        teacherUser.setFirstName("Jane");
        teacherUser.setLastName("Smith");
        teacherUser.setDateOfBirth(LocalDate.of(1980, 1, 1));
        teacherUser.setGender(Gender.FEMALE);
        teacherUser.setPhone("0987654321");
        teacherUser.setEmail("jane.smith@example.com");
        teacherUser.setUsername("janesmith");
        teacherUser.setPassword("password");
        teacherUser = userRepository.save(teacherUser);

        // Create and save a teacher entity
        teacher = new TeacherEntity();
        teacher.setUserEntity(teacherUser);
        teacher.setSchool(school);
        teacher = teacherRepository.save(teacher);
    }

    @Test
    void testFindByIdAndSchoolId() {
        // Create and save a class entity
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("Class 1");
        classEntity.setLevel(ClassLevel.FIRST);
        classEntity.setSchool(school);
        classEntity.setMentor(teacher);
        classEntity = classRepository.save(classEntity);

        Optional<ClassEntity> foundClass = classRepository.findByIdAndSchoolId(classEntity.getId(), school.getId());

        assertThat(foundClass).isPresent();
        assertThat(foundClass.get().getId()).isEqualTo(classEntity.getId());
        assertThat(foundClass.get().getSchool().getId()).isEqualTo(school.getId());
    }

    @Test
    void testFindBySchoolId() {
        // Create and save a class entity
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("Class 1");
        classEntity.setLevel(ClassLevel.FIRST);
        classEntity.setSchool(school);
        classEntity.setMentor(teacher);
        classEntity = classRepository.save(classEntity);

        List<ClassEntity> classes = classRepository.findBySchoolId(school.getId());

        assertThat(classes).isNotEmpty();
        assertThat(classes.get(0).getSchool().getId()).isEqualTo(school.getId());
    }

    @Test
    void testSaveClassEntity() {
        ClassEntity newClass = new ClassEntity();
        newClass.setName("Class 2");
        newClass.setLevel(ClassLevel.SECOND);
        newClass.setSchool(school);
        newClass.setMentor(teacher);

        ClassEntity savedClass = classRepository.save(newClass);

        assertThat(savedClass).isNotNull();
        assertThat(savedClass.getId()).isNotNull();
        assertThat(savedClass.getName()).isEqualTo("Class 2");
    }

    @Test
    void testUpdateClassEntity() {
        // Create and save a class entity
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("Class 1");
        classEntity.setLevel(ClassLevel.FIRST);
        classEntity.setSchool(school);
        classEntity.setMentor(teacher);
        classEntity = classRepository.save(classEntity);

        classEntity.setName("Updated Class 1");
        ClassEntity updatedClass = classRepository.save(classEntity);

        Optional<ClassEntity> foundClass = classRepository.findById(classEntity.getId());

        assertThat(foundClass).isPresent();
        assertThat(foundClass.get().getName()).isEqualTo("Updated Class 1");
    }

    @Test
    void testDeleteClassEntity() {
        // Create and save a class entity
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("Class 1");
        classEntity.setLevel(ClassLevel.FIRST);
        classEntity.setSchool(school);
        classEntity.setMentor(teacher);
        classEntity = classRepository.save(classEntity);

        classRepository.delete(classEntity);

        Optional<ClassEntity> deletedClass = classRepository.findById(classEntity.getId());

        assertThat(deletedClass).isNotPresent();
    }

    @Test
    void testFindAllClasses() {
        // Create and save a class entity
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("Class 1");
        classEntity.setLevel(ClassLevel.FIRST);
        classEntity.setSchool(school);
        classEntity.setMentor(teacher);
        classRepository.save(classEntity);

        List<ClassEntity> classes = classRepository.findAll();

        assertThat(classes).isNotEmpty();
    }

    @Test
    void testFindByIdAndSchoolId_NotFound() {
        Optional<ClassEntity> foundClass = classRepository.findByIdAndSchoolId(-1L, school.getId());

        assertThat(foundClass).isNotPresent();
    }

    @Test
    void testFindBySchoolId_NoClasses() {
        SchoolEntity newSchool = new SchoolEntity();
        newSchool.setName("New School");
        newSchool.setAddress("456 New St");
        newSchool = schoolRepository.save(newSchool);

        List<ClassEntity> classes = classRepository.findBySchoolId(newSchool.getId());

        assertThat(classes).isEmpty();
    }

    @Test
    void testUniqueConstraintOnMentor() {
        // Create and save a class entity
        ClassEntity classEntity1 = new ClassEntity();
        classEntity1.setName("Class 1");
        classEntity1.setLevel(ClassLevel.FIRST);
        classEntity1.setSchool(school);
        classEntity1.setMentor(teacher);
        classRepository.save(classEntity1);

        // Attempt to create another class entity with the same mentor
        ClassEntity classEntity2 = new ClassEntity();
        classEntity2.setName("Class 2");
        classEntity2.setLevel(ClassLevel.SECOND);
        classEntity2.setSchool(school);
        classEntity2.setMentor(teacher);

        try {
            classRepository.save(classEntity2);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}