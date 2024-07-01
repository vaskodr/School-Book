package com.nbu.schoolbook.class_entity.dto;

import com.nbu.schoolbook.enums.ClassLevel;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Set;

@Data
public class ClassDTO {
    private Long id;
    private String name;
    private ClassLevel level;
    private Long mentorId;
    private Long schoolId;
    private String schoolName;
}
