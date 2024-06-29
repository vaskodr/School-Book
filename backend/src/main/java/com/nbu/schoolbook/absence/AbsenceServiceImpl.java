package com.nbu.schoolbook.absence;


import com.nbu.schoolbook.absence.dto.AbsenceDTO;
import com.nbu.schoolbook.absence.dto.CreateAbsenceDTO;
import com.nbu.schoolbook.absence.dto.UpdateAbsenceDTO;
import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AbsenceServiceImpl implements AbsenceService{

}
