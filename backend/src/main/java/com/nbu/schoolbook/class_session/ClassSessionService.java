package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;

import java.util.List;

public interface ClassSessionService {
    ClassSessionDTO createClassSession(CreateClassSessionDTO createClassSessionDTO);
    ClassSessionDTO getClassSessionById(Long id);
    List<ClassSessionDTO> getAllClassSessions();
    ClassSessionDTO updateClassSession(Long id, UpdateClassSessionDTO updateClassSessionDTO);
    void deleteClassSession(Long id);
}
