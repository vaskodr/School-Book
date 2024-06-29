package com.nbu.schoolbook.class_entity;

import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.ClassDetailsDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.class_entity.dto.UpdateClassDTO;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentMapper;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.student.dto.StudentDetailsDTO;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClassServiceImpl implements ClassService{
    private final ClassMapper classMapper;
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

//    @Override
//    public ClassDTO createClass(CreateClassDTO createClassDTO) {
//        ClassEntity classEntity = new ClassEntity();
//
//        classEntity.setName(createClassDTO.getName());
//        classEntity.setLevel(createClassDTO.getLevel());
//
//        SchoolEntity school = schoolRepository.findById(createClassDTO.getSchoolId())
//                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
//        classEntity.setSchool(school);
//
//        TeacherEntity mentor = teacherRepository.findById(createClassDTO.getMentorId())
//                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
//        classEntity.setMentor(mentor);
//
//        Set<StudentEntity> students = createClassDTO.getStudentIds().stream()
//                .map(id -> studentRepository.findById(id)
//                        .orElseThrow(() -> new ResourceNotFoundException("Student not found")))
//                .collect(Collectors.toSet());
//        classEntity.setStudents(students);
//
//        ClassEntity savedClass = classRepository.save(classEntity);
//        return classMapper.mapToDTO(savedClass);
//    }

    @Override
    public ClassDTO getClassById(Long id) {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        return classMapper.mapToDTO(classEntity);
    }

    @Override
    public List<ClassDTO> getAllClasses() {
        List<ClassEntity> classes = classRepository.findAll();
        return classes.stream()
                .map(cls -> classMapper.mapToDTO(cls))
                .collect(Collectors.toList());
    }

    @Override
    public ClassDTO updateClass(Long id, UpdateClassDTO updateClassDTO) {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        classEntity.setName(updateClassDTO.getName());
        classEntity.setLevel(updateClassDTO.getLevel());

        SchoolEntity school = schoolRepository.findById(updateClassDTO.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
        classEntity.setSchool(school);

        TeacherEntity mentor = teacherRepository.findById(updateClassDTO.getMentorId())
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        classEntity.setMentor(mentor);

        Set<StudentEntity> students = updateClassDTO.getStudentIds().stream()
                .map(studentId -> studentRepository.findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found")))
                .collect(Collectors.toSet());
        classEntity.setStudents(students);

        ClassEntity updatedClass = classRepository.save(classEntity);
        return classMapper.mapToDTO(updatedClass);
    }

    @Override
    public void deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            throw new ResourceNotFoundException("Class not found");
        }
        classRepository.deleteById(id);
    }

    @Override
    public ClassEntity getClassIfProvided(RegisterDTO registerDTO) {
        if (registerDTO.getClassId() != null) {
            ClassEntity mentorClass = classRepository.findById(registerDTO.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found!"));

            if (mentorClass.getMentor() != null) {
                throw new RuntimeException("Class already has a mentor!");
            }
            return mentorClass;
        }
        return null;
    }

    public List<ClassDTO> getAllClassesBySchoolId(Long schoolId) {
        List<ClassEntity> classEntities = classRepository.findBySchoolId(schoolId);
        return classEntities.stream()
                .map(classMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public ClassDetailsDTO getClassBySchoolIdAndClassId(Long schoolId, Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndSchoolId(schoolId, classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        ClassDetailsDTO classDetailsDTO = new ClassDetailsDTO();
        classDetailsDTO.setId(classEntity.getId());
        classDetailsDTO.setName(classEntity.getName());
        classDetailsDTO.setLevel(classEntity.getLevel());

        classDetailsDTO.setMentorName(
                classEntity.getMentor().getUserEntity().getFirstName()
                + " " +
                classEntity.getMentor().getUserEntity().getLastName()
        );

        List<StudentDetailsDTO> students = classEntity.getStudents().stream()
                .map(studentMapper::mapToDetailsDTO)
                .toList();
        classDetailsDTO.setStudentDetailsDTOS(students);

        return classDetailsDTO;
    }
}
