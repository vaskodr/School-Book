package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;

import java.util.List;

public interface TeacherService {
    TeacherDTO createTeacher(CreateTeacherDTO createTeacherDTO);
    TeacherDTO getTeacherById(Long id);
    List<TeacherDTO> getAllTeachers();
    TeacherDTO updateTeacher(Long id, UpdateTeacherDTO updateTeacherDTO);
    void deleteTeacher(Long id);

}
