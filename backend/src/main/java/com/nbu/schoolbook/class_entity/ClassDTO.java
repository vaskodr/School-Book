package com.nbu.schoolbook.class_entity;

public record ClassDTO(
        Long id,
        String name,
        String level,
        Long schoolId,
        Long teacherId
) {
}
