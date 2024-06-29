package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.ClassDetailsDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.class_entity.dto.UpdateClassDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;

import java.util.List;

public interface ClassService {
    //ClassDTO createClass(CreateClassDTO createClassDTO);
    ClassDTO createClass(Long schoolId, CreateClassDTO createClassDTO);
    ClassDTO getClassById(Long id);
    List<ClassDTO> getAllClasses();
    ClassDTO updateClass(Long id, UpdateClassDTO updateClassDTO);
    void deleteClass(Long id);



    ClassEntity getClassIfProvided(RegisterDTO registerDTO);
    List<ClassDTO> getAllClassesBySchoolId(Long schoolId);
    ClassDetailsDTO getClassBySchoolIdAndClassId(Long schoolId, Long classId);
}
