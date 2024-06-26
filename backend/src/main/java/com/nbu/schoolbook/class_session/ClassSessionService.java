package com.nbu.schoolbook.class_session;

import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.UpdateClassSessionDTO;

import java.util.List;

public interface ClassSessionService {
    void createClassSession(CreateClassSessionDTO createClassSessionDTO, Long programId);
    ClassSessionDTO getClassSessionById(Long id);
    List<ClassSessionDTO> getAllClassSessions();
    void updateClassSession(Long id, UpdateClassSessionDTO updateClassSessionDTO);
    void deleteClassSession(Long id);
}
