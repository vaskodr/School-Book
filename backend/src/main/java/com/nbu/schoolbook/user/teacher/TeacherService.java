package com.nbu.schoolbook.user.teacher;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.UpdateTeacherDTO;

import java.util.List;
import java.util.Set;

public interface TeacherService {
    void registerTeacher(RegisterDTO registerDTO, Long schoolId);
    TeacherDTO getTeacherById(Long schoolId, Long teacherId);
    List<TeacherDTO> getAllTeachersBySchoolId(Long schoolId);
    void updateTeacher(Long schoolId, Long teacherId, UpdateTeacherDTO updateTeacherDTO);
    void deleteTeacher(Long schoolId, Long teacherId);

    // TODO: methods assignSubjectToTeacher
    void assignClassMentor(TeacherEntity teacher, ClassEntity mentorClass);
    void handleSubjectAssociation(RegisterDTO registerDTO, TeacherEntity teacher);

    List<TeacherDTO> getAvailableMentors(Long schoolId);

//    List<ListTeacherDTO> getAllTeachersBySchoolId();


    TeacherDTO getStudentByUserID(String userId);
    List<SubjectDTO> findSubjectsBySchoolIdAndTeacherId(Long schoolId, Long teacherId);

}
