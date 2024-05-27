package com.nbu.schoolbook.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDTO {
    private String name;
    private String address;
    private Long directorId;
    private List<Long> classIds;
    private List<Long> teacherIds;
}
