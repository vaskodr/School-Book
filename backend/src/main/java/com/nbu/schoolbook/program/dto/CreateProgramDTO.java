package com.nbu.schoolbook.program.dto;

import java.util.Set;

public class CreateProgramDTO {
    private Long classId;
    private Set<Long> classSessionIds;

    // Getters and Setters


    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Set<Long> getClassSessionIds() {
        return classSessionIds;
    }

    public void setClassSessionIds(Set<Long> classSessionIds) {
        this.classSessionIds = classSessionIds;
    }
}
