package com.nbu.schoolbook.repository.dto.school;

import com.nbu.schoolbook.repository.dto.classes.ClassDTO;
import com.nbu.schoolbook.repository.dto.teacher.TeacherDTO;

import java.util.Set;

public record SchoolDTO(
        Long id,
        String name,
        String address
) {

}
