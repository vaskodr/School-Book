package com.nbu.schoolbook.service;

import com.nbu.schoolbook.repository.dto.school.CreateSchoolDTO;
import com.nbu.schoolbook.repository.dto.school.SchoolDTO;
import com.nbu.schoolbook.repository.dto.school.UpdateSchoolDTO;

public interface SchoolService {
    CreateSchoolDTO createSchool(CreateSchoolDTO createSchoolDTO);
    UpdateSchoolDTO updateSchool(UpdateSchoolDTO updateSchoolDTO);




}
