package com.nbu.schoolbook.Repositories;

import com.nbu.schoolbook.absence.AbsenceEntity;
import com.nbu.schoolbook.absence.AbsenceRepository;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.enums.ClassLevel;
import com.nbu.schoolbook.enums.DayOfWeek;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.program.ProgramEntity;
import com.nbu.schoolbook.program.ProgramRepository;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AbsenceRepositoryTest {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgramRepository programRepository;

    private StudentEntity student;
    private ClassSessionEntity classSession;

    @BeforeEach
    @Rollback(false)
    void setUp() {
        // Create and save a school entity
        SchoolEntity school = new SchoolEntity();
        school.setName("Test School");
        school.setAddress("123 Test St");
        school = schoolRepository.save(school);

        // Create and save a class entity
        ClassEntity classEntity = new ClassEntity();
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
        TeacherEntity teacher = new TeacherEntity();
        teacher.setUserEntity(teacherUser);
        teacher.setSchool(school);
        teacher = teacherRepository.save(teacher);

        // Create and save a subject entity
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Mathematics");
        subject = subjectRepository.save(subject);

        // Create and save a program entity
        ProgramEntity program = new ProgramEntity();
        program.setAssociatedClass(classEntity);
        program = programRepository.save(program);

        // Create and save a class session entity
        classSession = new ClassSessionEntity();
        classSession.setDay(DayOfWeek.MONDAY);
        classSession.setStartTime(LocalTime.of(9, 0));
        classSession.setEndTime(LocalTime.of(10, 0));
        classSession.setTeacher(teacher);
        classSession.setSubject(subject);
        classSession.setProgram(program);
        classSession = classSessionRepository.save(classSession);
    }

    @Test
    void testFindAllByStudentId() {
        // Create and save an absence entity
        AbsenceEntity absence = new AbsenceEntity();
        absence.setDescription("Sick leave");
        absence.setDate(LocalDate.of(2023, 1, 1));
        absence.setClassSession(classSession);
        absence.setStudent(student);
        absence = absenceRepository.save(absence);

        List<AbsenceEntity> absences = absenceRepository.findAllByStudentId(student.getId());

        assertThat(absences).isNotEmpty();
        assertThat(absences.get(0).getStudent().getId()).isEqualTo(student.getId());
    }

    @Test
    void testSaveAbsenceEntity() {
        AbsenceEntity absence = new AbsenceEntity();
        absence.setDescription("Family emergency");
        absence.setDate(LocalDate.of(2023, 2, 1));
        absence.setClassSession(classSession);
        absence.setStudent(student);

        AbsenceEntity savedAbsence = absenceRepository.save(absence);

        assertThat(savedAbsence).isNotNull();
        assertThat(savedAbsence.getId()).isNotNull();
        assertThat(savedAbsence.getDescription()).isEqualTo("Family emergency");
    }

    @Test
    void testUpdateAbsenceEntity() {
        // Create and save an absence entity
        AbsenceEntity absence = new AbsenceEntity();
        absence.setDescription("Sick leave");
        absence.setDate(LocalDate.of(2023, 1, 1));
        absence.setClassSession(classSession);
        absence.setStudent(student);
        absence = absenceRepository.save(absence);

        absence.setDescription("Updated Sick leave");
        AbsenceEntity updatedAbsence = absenceRepository.save(absence);

        Optional<AbsenceEntity> foundAbsence = absenceRepository.findById(absence.getId());

        assertThat(foundAbsence).isPresent();
        assertThat(foundAbsence.get().getDescription()).isEqualTo("Updated Sick leave");
    }

    @Test
    void testDeleteAbsenceEntity() {
        // Create and save an absence entity
        AbsenceEntity absence = new AbsenceEntity();
        absence.setDescription("Sick leave");
        absence.setDate(LocalDate.of(2023, 1, 1));
        absence.setClassSession(classSession);
        absence.setStudent(student);
        absence = absenceRepository.save(absence);

        absenceRepository.delete(absence);

        Optional<AbsenceEntity> deletedAbsence = absenceRepository.findById(absence.getId());

        assertThat(deletedAbsence).isNotPresent();
    }

    @Test
    void testFindAllAbsences() {
        // Create and save an absence entity
        AbsenceEntity absence = new AbsenceEntity();
        absence.setDescription("Sick leave");
        absence.setDate(LocalDate.of(2023, 1, 1));
        absence.setClassSession(classSession);
        absence.setStudent(student);
        absenceRepository.save(absence);

        List<AbsenceEntity> absences = absenceRepository.findAll();

        assertThat(absences).isNotEmpty();
    }

    @Test
    void testFindAllByStudentId_NoAbsences() {
        List<AbsenceEntity> absences = absenceRepository.findAllByStudentId(student.getId());

        assertThat(absences).isEmpty();
    }
}
