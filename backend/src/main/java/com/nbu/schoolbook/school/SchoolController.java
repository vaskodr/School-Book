package com.nbu.schoolbook.school;

import com.nbu.schoolbook.class_entity.ClassService;
import com.nbu.schoolbook.class_entity.dto.ClassDTO;
import com.nbu.schoolbook.class_entity.dto.ClassDetailsDTO;
import com.nbu.schoolbook.class_entity.dto.CreateClassDTO;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.program.ProgramService;
import com.nbu.schoolbook.program.dto.CreateProgramDTO;
import com.nbu.schoolbook.program.dto.ProgramDTO;
import com.nbu.schoolbook.school.dto.CreateSchoolDTO;
import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.parent.dto.CreateParentDTO;
import com.nbu.schoolbook.user.student.dto.CreateStudentDTO;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import com.nbu.schoolbook.user.teacher.dto.CreateTeacherDTO;
import com.nbu.schoolbook.user.teacher.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService schoolService;
    private final ProgramService programService;
    private final ClassService classService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody CreateSchoolDTO createSchoolDTO) {
        SchoolDTO schoolDTO = schoolService.createSchool(createSchoolDTO);
        return ResponseEntity.ok(schoolDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable("id") Long id) {
        SchoolDTO schoolDTO = schoolService.getSchoolById(id);
        return ResponseEntity.ok(schoolDTO);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        List<SchoolDTO> schoolDTOs = schoolService.getAllSchools();
        return ResponseEntity.ok(schoolDTOs);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable("id") Long id, @RequestBody SchoolDTO schoolDTO) {
        SchoolDTO updatedSchool = schoolService.updateSchool(id, schoolDTO);
        return ResponseEntity.ok(updatedSchool);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable("id") Long id) {
        try{
             schoolService.deleteSchool(id);
             return ResponseEntity.noContent().build();
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{schoolId}/add-class")
    public ResponseEntity<ClassDTO> addClass(@PathVariable Long schoolId, @RequestBody CreateClassDTO createClassDTO) {
        ClassDTO classDTO = schoolService.addClass(schoolId, createClassDTO);
        return ResponseEntity.ok(classDTO);
    }

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{schoolId}/classes/{classId}/program")
    public ResponseEntity<ProgramDTO> createProgram(
            @PathVariable Long schoolId,
            @PathVariable Long classId,
            @RequestBody CreateProgramDTO createProgramDTO) {
        ProgramDTO programDTO = programService.createProgram(schoolId, classId, createProgramDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(programDTO);
    }

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

    @GetMapping("/{schoolId}/classes")
    public ResponseEntity<List<ClassDTO>> getAllClassesBySchoolId(@PathVariable Long schoolId) {
        List<ClassDTO> classDTOs = classService.getAllClassesBySchoolId(schoolId);
        return ResponseEntity.ok(classDTOs);
    }
    @GetMapping("/{schoolId}/classes/{classId}")
    public ResponseEntity<ClassDetailsDTO> getClassBySchoolIdAndClassId(@PathVariable Long schoolId, @PathVariable Long classId) {
        ClassDetailsDTO classDetailsDTO = classService.getClassBySchoolIdAndClassId(schoolId, classId);
        return ResponseEntity.ok(classDetailsDTO);
    }







}
