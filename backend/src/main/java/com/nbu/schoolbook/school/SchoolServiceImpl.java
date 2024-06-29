package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassMapper;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.school.dto.UpdateSchoolDTO;
import com.nbu.schoolbook.user.director.DirectorService;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.student.StudentService;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import com.nbu.schoolbook.user.teacher.TeacherService;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;
    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final DirectorService directorService;
    private final ClassMapper classMapper;
    private final StudentRepository studentRepository;

    @Override
    public void createSchool(CreateSchoolDTO createSchoolDTO) {
       SchoolEntity school = schoolMapper.mapCreateToEntity(createSchoolDTO);
       schoolRepository.save(school);
    }

    @Override
    public SchoolDTO getSchoolById(long id) {
        SchoolEntity school = schoolRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "School does not found with id: " + id
                        )
                );

        return schoolMapper.mapToDTO(school);
    }

    @Override
    public List<SchoolDTO> getAllSchools() {
        return schoolRepository.findAll()
                .stream()
                .map(schoolMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateSchool(long id, UpdateSchoolDTO updateSchoolDTO) {
        SchoolEntity school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
        if (updateSchoolDTO.getName() != null) {
            school.setName(updateSchoolDTO.getName());
        }
        if (updateSchoolDTO.getAddress() != null) {
            school.setAddress(updateSchoolDTO.getAddress());
        }
        schoolRepository.save(school);
    }

    @Override
    public void deleteSchool(long id) {
        if (!schoolRepository.existsById(id)) {
            throw new ResourceNotFoundException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }


//    @Override
//    public DirectorDTO addDirector(RegisterDTO registerDTO, Long schoolId) {
//        return directorService.registerDirector(registerDTO, schoolId);
//    }
//
//    @Override
//    public TeacherDTO addTeacher(RegisterDTO registerDTO, Long schoolId) {
//        return teacherService.registerTeacher(registerDTO, schoolId);
//    }
//
//    @Override
//    public StudentDTO addStudent(RegisterDTO registerDTO, Long classId) {
//        return studentService.registerStudent(registerDTO, classId);
//    }

//    @Override
//    public boolean addParent() {
//        return false;
//    }

//    @Override
//    public ClassDTO addClass(Long schoolId, CreateClassDTO createClassDTO) {
//        SchoolEntity school = schoolRepository.findById(schoolId)
//                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
//
//        ClassEntity newClass = new ClassEntity();
//        newClass.setName(createClassDTO.getName());
//        newClass.setLevel(createClassDTO.getLevel());
//        newClass.setSchool(school);
//
//
//        ClassEntity savedClass = classRepository.save(newClass);
//        return classMapper.mapToDTO(savedClass);
//    }


//    @Override
//    public List<ClassDTO> getClassesBySchoolId(Long schoolId) {
//        SchoolEntity school = schoolRepository.findById(schoolId)
//                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
//
//        List<ClassEntity> classEntities = classRepository.findBySchool(school);
//
//        return classEntities.stream()
//                .map(classMapper::mapToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public void enrollStudentToClass(Long schoolId, Long classId, Long studentId) {
        SchoolEntity school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found!"));
        ClassEntity studentClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));
        studentClass.getStudents().add(student);
        student.setStudentClass(studentClass);
        classRepository.save(studentClass);
        studentRepository.save(student);


    }

    @Override
    @Transactional
    public void unenrollStudentFromClass(Long schoolId, Long classId, Long studentId) {
        SchoolEntity school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found!"));
        ClassEntity studentClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        studentClass.getStudents().remove(student);
        student.setStudentClass(null);
        classRepository.save(studentClass);
        studentRepository.save(student);
    }

}
