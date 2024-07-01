package com.nbu.schoolbook.user.parent;

import java.util.List;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import com.nbu.schoolbook.user.parent.dto.UpdateParentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;

public interface ParentService {
    ParentDTO createParent(RegisterDTO registerDTO);
    ParentDTO getParentById(Long id);
    List<ParentDTO> getAllParents();
    ParentDTO updateParent(Long id, UpdateParentDTO updateParentDTO);
    void deleteParent(Long id);
    public List<Long> getParents(Long studentId);
    List<StudentDTO> getParentChildrenByUserId(String userId);
    void unassignParent(Long parentId, Long studentId);
}
