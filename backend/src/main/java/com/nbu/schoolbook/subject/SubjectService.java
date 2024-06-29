package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;
import com.nbu.schoolbook.subject.dto.UpdateSubjectDTO;

import java.util.List;

public interface SubjectService {
    void createSubject(CreateSubjectDTO subjectDTO);
    SubjectDTO getSubjectById(Long id);
    List<SubjectDTO> getAllSubjects();
    void updateSubject(Long id, UpdateSubjectDTO updateSubjectDTO);
    void deleteSubject(Long id);

}
