package com.nbu.schoolbook.repository.dto.classes;

import com.nbu.schoolbook.models.enums.ClassLevel;

import java.io.Serializable;

public record ClassDTO(
        Long id,
        String name,
        ClassLevel level,
        Long schoolId,
        String schoolName,
        Long mentorId,
        String mentorName
) implements Serializable {
}
