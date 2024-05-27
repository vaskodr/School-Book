package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.ClassEntity;
import com.nbu.schoolbook.class_entity.ClassRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public CreateSchoolDTO createSchool(CreateSchoolDTO createSchoolDTO) {
       SchoolEntity school = schoolMapper.mapCreateDTOToEntity(createSchoolDTO);
       school = schoolRepository.save(school);
       return schoolMapper.mapEntityToCreateDTO(school);
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
    public SchoolDTO updateSchool(long id, SchoolDTO updateSchool) {
        SchoolEntity school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        if (updateSchool.getName() != null) {
            school.setName(updateSchool.getName());
        }
        if (updateSchool.getAddress() != null) {
            school.setAddress(updateSchool.getAddress());
        }
        if (updateSchool.getClassIds() != null) {
            Set<ClassEntity> classes = new HashSet<>(
                    classRepository.findAllById(updateSchool.getClassIds())
            );
            school.setClasses(classes);
        }
        if (updateSchool.getTeacherIds() != null) {
            Set<TeacherEntity> teachers = new HashSet<>(
                    teacherRepository.findAllById(updateSchool.getTeacherIds())
            );
            school.setTeachers(teachers);
        }

        school = schoolRepository.save(school);
        return schoolMapper.mapToDTO(school);
    }

    @Override
    public void deleteSchool(long id) {
        if (!schoolRepository.existsById(id)) {
            throw new ResourceNotFoundException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }

}
