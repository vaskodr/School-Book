package com.nbu.schoolbook.program.dto;

import java.util.Set;

public class ProgramDTO {
    private Long id;
    private Long classId;
    private Set<Long> classSessionIds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
