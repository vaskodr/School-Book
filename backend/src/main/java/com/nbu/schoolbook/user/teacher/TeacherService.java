package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.ListTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;

import java.util.List;

public interface TeacherService {
    TeacherDTO registerTeacher(RegisterDTO registerDTO, Long schoolId);
    TeacherDTO getTeacherById(Long id);
    List<TeacherDTO> getAllTeachers();
    TeacherDTO updateTeacher(Long id, UpdateTeacherDTO updateTeacherDTO);
    void deleteTeacher(Long id);

    // TODO: methods assignSubjectToTeacher
    void assignClassMentor(TeacherEntity teacher, ClassEntity mentorClass);
    void handleSubjectAssociation(RegisterDTO registerDTO, TeacherEntity teacher);

//    List<ListTeacherDTO> getAllTeachersBySchoolId();

}
