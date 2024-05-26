package com.nbu.schoolbook.school.dto;

import com.nbu.schoolbook.class_entity.ClassDTO;
import com.nbu.schoolbook.user.teacher.TeacherDTO;

import java.util.List;

public record SchoolDTO(
        Long id,
        String name,
        String address,
        Long directorId,
        List<ClassDTO> classes,
        List<TeacherDTO> teachers
) {
}
