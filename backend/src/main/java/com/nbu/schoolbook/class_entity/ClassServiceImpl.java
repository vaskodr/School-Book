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

    @Override
    public void createClass(Long schoolId, CreateClassDTO createClassDTO) {
        SchoolEntity school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        ClassEntity newClass = new ClassEntity();
        newClass.setName(createClassDTO.getName());
        newClass.setLevel(createClassDTO.getLevel());
        newClass.setSchool(school);

        classRepository.save(newClass);
    }

    @Override
    public ClassDTO getClassById(Long schoolId, Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndSchoolId(schoolId, classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found for the specified school"));
        return classMapper.mapToDTO(classEntity);
    }

    @Override
    public List<ClassDTO> getAllClasses(Long schoolId) {
        List<ClassEntity> classes = classRepository.findBySchoolId(schoolId);
        return classes.stream()
                .map(classMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateClass(Long schoolId, Long classId, UpdateClassDTO updateClassDTO) {
        ClassEntity classEntity = classRepository.findByIdAndSchoolId(classId, schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found for the given school"));

        classEntity.setName(updateClassDTO.getName());
        classEntity.setLevel(updateClassDTO.getLevel());

        SchoolEntity school = schoolRepository.findById(schoolId)
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

        classRepository.save(classEntity);
    }

    @Override
    public void deleteClass(Long schoolId, Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndSchoolId(classId, schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found for the given school"));

        classRepository.deleteById(classId);
    }

//    @Override
//    public ClassEntity getClassIfProvided(RegisterDTO registerDTO) {
//        if (registerDTO.getClassId() != null) {
//            ClassEntity mentorClass = classRepository.findById(registerDTO.getClassId())
//                    .orElseThrow(() -> new RuntimeException("Class not found!"));
//
//            if (mentorClass.getMentor() != null) {
//                throw new RuntimeException("Class already has a mentor!");
//            }
//            return mentorClass;
//        }
//        return null;
//    }

}
