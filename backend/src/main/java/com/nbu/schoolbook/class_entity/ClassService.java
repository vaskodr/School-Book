package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.ClassDetailsDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.class_entity.dto.UpdateClassDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;

import java.util.List;

public interface ClassService {
    //ClassDTO createClass(CreateClassDTO createClassDTO);
    void createClass(Long schoolId, CreateClassDTO createClassDTO);
    ClassDTO getClassById(Long schoolId, Long classId);
    List<ClassDTO> getAllClasses(Long schoolId);
    void updateClass(Long schoolId, Long classId, UpdateClassDTO updateClassDTO);
    void deleteClass(Long schoolId, Long classId);
}
