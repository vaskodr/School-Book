package com.nbu.schoolbook.school;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbu.schoolbook.class_entity.ClassService;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.program.ProgramService;
import com.nbu.schoolbook.program.dto.ProgramDTO;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.school.dto.UpdateSchoolDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;
    private final ProgramService programService;
    private final ClassService classService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createSchool(@RequestBody CreateSchoolDTO createSchoolDTO) {
        schoolService.createSchool(createSchoolDTO);
        return ResponseEntity.ok("School with name: " + createSchoolDTO.getName() + " created successfully");
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable("schoolId") Long schoolId) {
        SchoolDTO schoolDTO = schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(schoolDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        List<SchoolDTO> schoolDTOs = schoolService.getAllSchools();
        return ResponseEntity.ok(schoolDTOs);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{schoolId}")
    public ResponseEntity<String> updateSchool(@PathVariable("schoolId") Long schoolId, @RequestBody UpdateSchoolDTO updateSchoolDTO) {
        schoolService.updateSchool(schoolId, updateSchoolDTO);
        return ResponseEntity.ok("School with id: " + schoolId + " updated successfully!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<String> deleteSchool(@PathVariable("schoolId") Long id) {
        try{
             schoolService.deleteSchool(id);
             return ResponseEntity.ok("School with id: " + id + " deleted successfully");
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{classId}/add-student")
//    public ResponseEntity<StudentDTO> addStudent(@PathVariable Long classId, @RequestBody RegisterDTO createStudentDTO){
//        StudentDTO studentDTO = schoolService.addStudent(createStudentDTO, classId);
//        return ResponseEntity.ok(studentDTO);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{schoolId}/add-teacher")
//    public ResponseEntity<TeacherDTO> addTeacher(@PathVariable Long schoolId, @RequestBody RegisterDTO createTeacherDTO){
//        TeacherDTO teacherDTO = schoolService.addTeacher(createTeacherDTO, schoolId);
//        return ResponseEntity.ok(teacherDTO);
//    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{studentId}/add-parent")
//    public ResponseEntity<String> addParent(@PathVariable Long studentId, RegisterDTO createParentDTO){
//        return ResponseEntity.ok().build();
//    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{schoolId}/add-director")
//    public ResponseEntity<DirectorDTO> addDirector(@PathVariable Long schoolId, @RequestBody RegisterDTO createDirectorDTO){
//        DirectorDTO directorDTO = schoolService.addDirector(createDirectorDTO, schoolId);
//        return ResponseEntity.ok(directorDTO);
//    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{schoolId}/add-class")
//    public ResponseEntity<ClassDTO> addClass(@PathVariable Long schoolId, @RequestBody CreateClassDTO createClassDTO) {
//        ClassDTO classDTO = schoolService.addClass(schoolId, createClassDTO);
//        return ResponseEntity.ok(classDTO);
//    }

//    @GetMapping("/{schoolId}/classes")
//    public ResponseEntity<List<ClassDTO>> getAllClassesBySchoolId(@PathVariable Long schoolId) {
//        List<ClassDTO> classDTOs = schoolService.getClassesBySchoolId(schoolId);
//        return ResponseEntity.ok(classDTOs);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{schoolId}/classes/{classId}/enroll-student/{studentId}")
    public ResponseEntity<String> enrollStudent(@PathVariable Long schoolId,
                                                @PathVariable Long classId,
                                                @PathVariable Long studentId) {
        schoolService.enrollStudentToClass(schoolId, classId, studentId);
        return ResponseEntity.ok("Student enrolled successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{schoolId}/classes/{classId}/unenroll-student/{studentId}")
    public ResponseEntity<String> unenrollStudent(@PathVariable Long schoolId,
                                                  @PathVariable Long classId,
                                                  @PathVariable Long studentId) {
        schoolService.unenrollStudentFromClass(schoolId, classId, studentId);
        return ResponseEntity.ok("Student unenrolled successfully");
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{schoolId}/classes/{classId}/program")
//    public ResponseEntity<ProgramDTO> createProgram(
//            @PathVariable Long schoolId,
//            @PathVariable Long classId,
//            @RequestBody CreateProgramDTO createProgramDTO) {
//        ProgramDTO programDTO = programService.createProgram(schoolId, classId, createProgramDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(programDTO);
//    }

    @GetMapping("/{schoolId}/classes/{classId}/program")
    public ResponseEntity<ProgramDTO> getProgramBySchoolAndClassId(@PathVariable Long schoolId, @PathVariable Long classId) {
        ProgramDTO programDTO = programService.getClassProgram(schoolId, classId);
        if (programDTO != null) {
            return ResponseEntity.ok(programDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{schoolId}/students/{studentId}/program")
    public ResponseEntity<ProgramDTO> getProgramBySchoolIdAndStudentId(@PathVariable Long schoolId, @PathVariable Long studentId) {
        ProgramDTO programDTO = programService.getClassProgramByStudentId(schoolId, studentId);
        return ResponseEntity.ok(programDTO);
    }

//    @GetMapping("/{schoolId}/classes")
//    public ResponseEntity<List<ClassDTO>> getAllClassesBySchoolId(@PathVariable Long schoolId) {
//        List<ClassDTO> classDTOs = classService.getAllClassesBySchoolId(schoolId);
//        return ResponseEntity.ok(classDTOs);
//    }
//    @GetMapping("/{schoolId}/classes/{classId}")
//    public ResponseEntity<ClassDetailsDTO> getClassBySchoolIdAndClassId(@PathVariable Long schoolId, @PathVariable Long classId) {
//        ClassDetailsDTO classDetailsDTO = classService.getClassBySchoolIdAndClassId(schoolId, classId);
//        return ResponseEntity.ok(classDetailsDTO);
//    }







}
