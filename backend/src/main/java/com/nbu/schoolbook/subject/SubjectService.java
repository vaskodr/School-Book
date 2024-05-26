package com.nbu.schoolbook.subject;

import java.util.List;

public interface SubjectService {
    SubjectEntity saveSubject(SubjectEntity subject);
    SubjectEntity updateSubject(long id, SubjectEntity subject);
    void deleteSubject(long id);
    SubjectEntity getSubjectById(long id);
    List<SubjectEntity> getAllSubjects();
}
