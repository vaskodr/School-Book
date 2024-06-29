package com.nbu.schoolbook.class_entity.dto;

import com.nbu.schoolbook.enums.ClassLevel;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class UpdateClassDTO {
    private String name;
    private ClassLevel level;
    private Long schoolId;
    private Long mentorId;
    private Set<Long> studentIds;
}
