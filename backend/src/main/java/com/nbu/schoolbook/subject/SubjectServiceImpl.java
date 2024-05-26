package com.nbu.schoolbook.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectEntity saveSubject(SubjectEntity subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public SubjectEntity updateSubject(long id, SubjectEntity subject) {
        Optional<SubjectEntity> existingSubject = subjectRepository.findById(id);
        if (existingSubject.isPresent()) {
            SubjectEntity updatedSubject = existingSubject.get();
            updatedSubject.setName(subject.getName());
            return subjectRepository.save(updatedSubject);
        } else {
            throw new RuntimeException("Subject not found with id: " + id);
        }
    }

    @Override
    public void deleteSubject(long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public SubjectEntity getSubjectById(long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    @Override
    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
