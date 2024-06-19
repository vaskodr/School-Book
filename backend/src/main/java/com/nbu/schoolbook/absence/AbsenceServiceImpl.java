package com.nbu.schoolbook.absence;


import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AbsenceServiceImpl implements AbsenceService{

    private final AbsenceRepository absenceRepository;
    private final AbsenceMapper absenceMapper;
    private final StudentRepository studentRepository;
    private final ClassSessionRepository classSessionRepository;


    @Override
    public AbsenceDTO createAbsence(CreateAbsenceDTO createAbsenceDTO) {
        AbsenceEntity absenceEntity = new AbsenceEntity();

        absenceEntity.setDescription(createAbsenceDTO.getDescription());
        absenceEntity.setDate(createAbsenceDTO.getDate());

        StudentEntity student = studentRepository.findById(createAbsenceDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        absenceEntity.setStudent(student);

        ClassSessionEntity classSession = classSessionRepository.findById(createAbsenceDTO.getClassSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found"));
        absenceEntity.setClassSession(classSession);

        AbsenceEntity savedAbsence = absenceRepository.save(absenceEntity);
        return absenceMapper.mapToDTO(savedAbsence);
    }

    @Override
    public AbsenceDTO getAbsenceById(Long id) {
        AbsenceEntity absenceEntity = absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not found"));
        return absenceMapper.mapToDTO(absenceEntity);
    }

    @Override
    public List<AbsenceDTO> getAllAbsences() {
        List<AbsenceEntity> absences = absenceRepository.findAll();
        return absences.stream()
                .map(absenceMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AbsenceDTO updateAbsence(Long id, UpdateAbsenceDTO updateAbsenceDTO) {
        AbsenceEntity absenceEntity = absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not found"));

        absenceEntity.setDescription(updateAbsenceDTO.getDescription());
        absenceEntity.setDate(updateAbsenceDTO.getDate());

        StudentEntity student = studentRepository.findById(updateAbsenceDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        absenceEntity.setStudent(student);

        ClassSessionEntity classSession = classSessionRepository.findById(updateAbsenceDTO.getClassSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Class session not found"));
        absenceEntity.setClassSession(classSession);

        AbsenceEntity updatedAbsence = absenceRepository.save(absenceEntity);
        return absenceMapper.mapToDTO(updatedAbsence);
    }

    @Override
    public void deleteAbsence(Long id) {
        if (!absenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Absence not found");
        }
        absenceRepository.deleteById(id);
    }
}
