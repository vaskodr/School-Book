package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.dto.CreateParentDTO;
import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import com.nbu.schoolbook.user.parent.dto.UpdateParentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;

import java.util.List;

public interface ParentService {
    ParentDTO createParent(RegisterDTO registerDTO);
    ParentDTO getParentById(Long id);
    List<ParentDTO> getAllParents();
    ParentDTO updateParent(Long id, UpdateParentDTO updateParentDTO);
    void deleteParent(Long id);

    List<StudentDTO> getParentChildren(Long parentId);

    List<Long> getParents(Long studentId);


}
