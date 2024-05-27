package com.nbu.schoolbook.school;

import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;

import java.util.List;

public interface SchoolService {
    CreateSchoolDTO createSchool(CreateSchoolDTO createSchoolDTO);
    SchoolDTO getSchoolById(long id);
    List<SchoolDTO> getAllSchools();
    SchoolDTO updateSchool(long id, SchoolDTO updateSchool);
    void deleteSchool(long id);

}
