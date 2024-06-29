package com.nbu.schoolbook.absence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.user.student.StudentRepository;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private AbsenceMapper absenceMapper;

    @Override
    public List<AbsenceDTO> getAbsencesByStudentId(Long studentId) {
        List<AbsenceEntity> absences = absenceRepository.findAllByStudentId(studentId);
        return absences.stream().map(absenceMapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AbsenceDTO createAbsence(CreateAbsenceDTO createAbsenceDTO) {
        AbsenceEntity absenceEntity = absenceMapper.mapCreateToEntity(createAbsenceDTO);
        absenceEntity.setStudent(studentRepository.findById(createAbsenceDTO.getStudentId()).orElseThrow());
        absenceEntity.setClassSession(classSessionRepository.findById(createAbsenceDTO.getClassSessionId()).orElseThrow());

        AbsenceEntity savedAbsence = absenceRepository.save(absenceEntity);
        return absenceMapper.mapToDTO(savedAbsence);
    }

    @Override
    public AbsenceDTO updateAbsence(Long absenceId, UpdateAbsenceDTO updateAbsenceDTO) {
        AbsenceEntity absenceEntity = absenceRepository.findById(absenceId).orElseThrow();
        absenceEntity.setDescription(updateAbsenceDTO.getDescription());
        absenceEntity.setDate(updateAbsenceDTO.getDate());
        absenceEntity.setStudent(studentRepository.findById(updateAbsenceDTO.getStudentId()).orElseThrow());
        absenceEntity.setClassSession(classSessionRepository.findById(updateAbsenceDTO.getClassSessionId()).orElseThrow());

        AbsenceEntity updatedAbsence = absenceRepository.save(absenceEntity);
        return absenceMapper.mapToDTO(updatedAbsence);
    }
}
