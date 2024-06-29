package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;

import java.util.List;

public interface SchoolService {
    SchoolDTO createSchool(CreateSchoolDTO createSchoolDTO);
    SchoolDTO getSchoolById(long id);
    List<SchoolDTO> getAllSchools();
    SchoolDTO updateSchool(long id, SchoolDTO updateSchool);
    void deleteSchool(long id);

//    TeacherDTO addTeacher(RegisterDTO registerDTO, Long schoolId);
//    StudentDTO addStudent(RegisterDTO registerDTO, Long classId);
//    DirectorDTO addDirector(RegisterDTO registerDTO, Long schoolId);
    // boolean addParent();
    ClassDTO addClass(Long schoolId, CreateClassDTO createClassDTO);
    List<ClassDTO> getClassesBySchoolId(Long schoolId);

    void enrollStudentToClass(Long schoolId, Long classId, Long studentId);
    void unenrollStudentFromClass(Long schoolId, Long classId, Long studentId);





}
