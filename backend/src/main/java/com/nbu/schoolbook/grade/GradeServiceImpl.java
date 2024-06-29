package com.nbu.schoolbook.grade;

import com.nbu.schoolbook.class_session.ClassSessionEntity;
import com.nbu.schoolbook.class_session.ClassSessionRepository;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.grade.dto.CreateGradeDTO;
import com.nbu.schoolbook.grade.dto.GradeDTO;
import com.nbu.schoolbook.grade.dto.UpdateGradeDTO;
import com.nbu.schoolbook.subject.SubjectEntity;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.student.StudentRepository;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

}
