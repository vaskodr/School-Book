package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.class_entity.dto.UpdateClassDTO;

import java.util.List;

public interface ClassService {
    ClassDTO createClass(CreateClassDTO createClassDTO);
    ClassDTO getClassById(Long id);
    List<ClassDTO> getAllClasses();
    ClassDTO updateClass(Long id, UpdateClassDTO updateClassDTO);
    void deleteClass(Long id);
}
