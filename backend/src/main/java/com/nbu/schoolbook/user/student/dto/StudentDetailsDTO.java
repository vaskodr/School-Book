package com.nbu.schoolbook.user.student.dto;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String email;
    private String username;
    private List<GradeDTO> grades;
    private List<AbsenceDTO> absences;
    private List<ParentDTO> parents;
    private String schoolName;
    private ClassDTO classDTO;
}
