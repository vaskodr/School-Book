package com.nbu.schoolbook.subject;

import com.nbu.schoolbook.subject.dto.CreateSubjectDTO;
import com.nbu.schoolbook.subject.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {
    CreateSubjectDTO saveSubject(CreateSubjectDTO subjectDTO);
    SubjectDTO getSubjectById(Long id);
    List<SubjectDTO> getAllSubjects();
    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);
    void deleteSubject(Long id);

}
