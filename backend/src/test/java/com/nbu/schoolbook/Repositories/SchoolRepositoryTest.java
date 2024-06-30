package com.nbu.schoolbook.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
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
class SchoolRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;

    private SchoolEntity school;

    @BeforeEach
    @Transactional
    @Rollback(false)
    void setUp() {
        // Create and save school entity
        school = new SchoolEntity();
        school.setName("Springfield Elementary");
        school.setAddress("742 Evergreen Terrace");
        schoolRepository.save(school);
    }

    @Test
    void testCreateSchool() {
        SchoolEntity newSchool = new SchoolEntity();
        newSchool.setName("Shelbyville Elementary");
        newSchool.setAddress("1234 Shelbyville Road");

        SchoolEntity savedSchool = schoolRepository.save(newSchool);

        assertThat(savedSchool).isNotNull();
        assertThat(savedSchool.getId()).isNotNull();
        assertThat(savedSchool.getName()).isEqualTo("Shelbyville Elementary");
        assertThat(savedSchool.getAddress()).isEqualTo("1234 Shelbyville Road");
    }

    @Test
    void testFindSchoolById() {
        Optional<SchoolEntity> foundSchool = schoolRepository.findById(school.getId());

        assertThat(foundSchool).isPresent();
        assertThat(foundSchool.get().getName()).isEqualTo("Springfield Elementary");
        assertThat(foundSchool.get().getAddress()).isEqualTo("742 Evergreen Terrace");
    }

    @Test
    void testUpdateSchool() {
        school.setName("Springfield Advanced Elementary");
        school.setAddress("123 New Address");

        SchoolEntity updatedSchool = schoolRepository.save(school);

        assertThat(updatedSchool.getName()).isEqualTo("Springfield Advanced Elementary");
        assertThat(updatedSchool.getAddress()).isEqualTo("123 New Address");
    }

    @Test
    void testDeleteSchool() {
        schoolRepository.delete(school);

        Optional<SchoolEntity> deletedSchool = schoolRepository.findById(school.getId());

        assertThat(deletedSchool).isNotPresent();
    }

    @Test
    void testFindAllSchools() {
        Iterable<SchoolEntity> schools = schoolRepository.findAll();

        assertThat(schools).hasSizeGreaterThanOrEqualTo(1);
    }
}
