package com.nbu.schoolbook.school;

import java.util.List;

public interface SchoolService {
    SchoolEntity saveSchool(SchoolEntity school);
    SchoolEntity updateSchool(long id, SchoolEntity school);
    void deleteSchool(long id);
    SchoolEntity getSchoolById(long id);
    List<SchoolEntity> getAllSchools();
}
