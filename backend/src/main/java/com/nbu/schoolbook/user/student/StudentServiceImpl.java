package com.nbu.schoolbook.user.student;

import com.nbu.schoolbook.absence.AbsenceEntity;
import com.nbu.schoolbook.absence.AbsenceRepository;
import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.grade.GradeEntity;
import com.nbu.schoolbook.grade.GradeRepository;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.parent.ParentRepository;
import com.nbu.schoolbook.user.student.dto.CreateStudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.student.dto.UpdateStudentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final AbsenceRepository absenceRepository;
    private final ParentRepository parentRepository;
    private final ClassRepository classRepository;




    @Override
    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        StudentEntity studentEntity = studentMapper.mapCreateDTOToEntity(createStudentDTO);

        // Handle null or empty gradeIds
        if (createStudentDTO.getGradeIds() != null && !createStudentDTO.getGradeIds().isEmpty()) {
            List<GradeEntity> grades = gradeRepository.findAllById(createStudentDTO.getGradeIds());
            studentEntity.setGrades(new HashSet<>(grades));
        }

        // Handle null or empty absenceIds
        if (createStudentDTO.getAbsenceIds() != null && !createStudentDTO.getAbsenceIds().isEmpty()) {
            List<AbsenceEntity> absences = absenceRepository.findAllById(createStudentDTO.getAbsenceIds());
            studentEntity.setAbsences(new HashSet<>(absences));
        }

        // Handle null or empty parentIds
        if (createStudentDTO.getParentIds() != null && !createStudentDTO.getParentIds().isEmpty()) {
            List<ParentEntity> parents = parentRepository.findAllById(createStudentDTO.getParentIds());
            studentEntity.setParents(new HashSet<>(parents));
        }

        // Handle null classId
        if (createStudentDTO.getClassId() != null) {
            ClassEntity studentClass = classRepository.findById(createStudentDTO.getClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
            studentEntity.setStudentClass(studentClass);
        }

        studentEntity = studentRepository.save(studentEntity);

        return studentMapper.mapEntityToDTO(studentEntity);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Student does not exists with id: " + id
                        )
                );
        return studentMapper.mapEntityToDTO(studentEntity);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(UpdateStudentDTO updateStudentDTO) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Student does not found!"
                        )
                );
        studentRepository.deleteById(id);
    }
}
