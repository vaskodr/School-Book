package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.grade.GradeEntity;
import com.nbu.schoolbook.grade.GradeRepository;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.enums.ClassLevel;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.teacher.TeacherRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GradeRepositoryTest {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private SchoolEntity school;
    private ClassEntity classEntity;
    private StudentEntity student;
    private TeacherEntity teacher;
    private SubjectEntity subject;
    private GradeEntity grade;

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

        // Create and save a user entity for student
        UserEntity studentUser = new UserEntity();
        studentUser.setId("1234567890");
        studentUser.setFirstName("John");
        studentUser.setLastName("Doe");
        studentUser.setDateOfBirth(LocalDate.of(2000, 1, 1));
        studentUser.setGender(Gender.MALE);
        studentUser.setPhone("1234567890");
        studentUser.setEmail("john.doe@example.com");
        studentUser.setUsername("johndoe");
        studentUser.setPassword("password");
        studentUser = userRepository.save(studentUser);

        // Create and save a student entity
        student = new StudentEntity();
        student.setUserEntity(studentUser);
        student.setStudentClass(classEntity);
        student = studentRepository.save(student);

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

        // Create and save a subject entity
        subject = new SubjectEntity();
        subject.setName("Mathematics");
        subject = subjectRepository.save(subject);

        // Create and save a grade entity
        grade = new GradeEntity();
        grade.setGrade(BigDecimal.valueOf(95));
        grade.setDate(LocalDate.now());
        grade.setStudent(student);
        grade.setTeacher(teacher);
        grade.setSubject(subject);
        grade = gradeRepository.save(grade);
    }

    @Test
    void testFindAllByStudentId() {
        List<GradeEntity> grades = gradeRepository.findAllByStudentId(student.getId());

        assertThat(grades).isNotEmpty();
        assertThat(grades.get(0).getStudent().getId()).isEqualTo(student.getId());
    }
}
