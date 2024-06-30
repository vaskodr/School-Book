package com.nbu.schoolbook.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    private SubjectEntity subject;

    @BeforeEach
    @Transactional
    @Rollback(false)
    void setUp() {
        // Create and save subject entity
        subject = new SubjectEntity();
        subject.setName("Mathematics");
        subjectRepository.save(subject);
    }

    @Test
    void testCreateSubject() {
        SubjectEntity newSubject = new SubjectEntity();
        newSubject.setName("Physics");

        SubjectEntity savedSubject = subjectRepository.save(newSubject);

        assertThat(savedSubject).isNotNull();
        assertThat(savedSubject.getId()).isNotNull();
        assertThat(savedSubject.getName()).isEqualTo("Physics");
    }

    @Test
    void testFindSubjectById() {
        Optional<SubjectEntity> foundSubject = subjectRepository.findById(subject.getId());

        assertThat(foundSubject).isPresent();
        assertThat(foundSubject.get().getName()).isEqualTo("Mathematics");
    }

    @Test
    void testUpdateSubject() {
        subject.setName("Advanced Mathematics");

        SubjectEntity updatedSubject = subjectRepository.save(subject);

        assertThat(updatedSubject.getName()).isEqualTo("Advanced Mathematics");
    }

    @Test
    void testDeleteSubject() {
        subjectRepository.delete(subject);

        Optional<SubjectEntity> deletedSubject = subjectRepository.findById(subject.getId());

        assertThat(deletedSubject).isNotPresent();
    }

    @Test
    void testFindAllSubjects() {
        Iterable<SubjectEntity> subjects = subjectRepository.findAll();

        assertThat(subjects).hasSizeGreaterThanOrEqualTo(1);
    }
}
