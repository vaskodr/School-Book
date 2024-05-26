package com.nbu.schoolbook.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public SchoolEntity saveSchool(SchoolEntity school) {
        return schoolRepository.save(school);
    }

    @Override
    public SchoolEntity updateSchool(long id, SchoolEntity school) {
        Optional<SchoolEntity> existingSchool = schoolRepository.findById(id);
        if (existingSchool.isPresent()) {
            SchoolEntity updatedSchool = existingSchool.get();
            updatedSchool.setName(school.getName());
            updatedSchool.setAddress(school.getAddress());
            updatedSchool.setDirector(school.getDirector());
            updatedSchool.setClasses(school.getClasses());
            updatedSchool.setTeachers(school.getTeachers());
            return schoolRepository.save(updatedSchool);
        } else {
            throw new RuntimeException("School not found with id: " + id);
        }
    }

    @Override
    public void deleteSchool(long id) {
        schoolRepository.deleteById(id);
    }

    @Override
    public SchoolEntity getSchoolById(long id) {
        return schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("School not found with id: " + id));
    }

    @Override
    public List<SchoolEntity> getAllSchools() {
        return schoolRepository.findAll();
    }
}
