package com.nbu.schoolbook.class_session;
import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.CreateClassSessionDTO;
import com.nbu.schoolbook.class_session.dto.SessionDTO;
import com.nbu.schoolbook.subject.SubjectRepository;
import com.nbu.schoolbook.subject.SubjectServiceImpl;
import com.nbu.schoolbook.user.teacher.TeacherRepository;
import com.nbu.schoolbook.user.teacher.TeacherServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ClassSessionMapper {
    private final ModelMapper modelMapper;

    public ClassSessionDTO mapToDTO(ClassSessionEntity classSession) {
        return modelMapper.map(classSession, ClassSessionDTO.class);
    }

    public ClassSessionEntity mapToEntity(ClassSessionDTO classSessionDTO) {
        return modelMapper.map(classSessionDTO, ClassSessionEntity.class);
    }

    public ClassSessionEntity mapCreateToEntity(CreateClassSessionDTO createClassSessionDTO) {
        return modelMapper.map(createClassSessionDTO, ClassSessionEntity.class);
    }

    public ClassSessionDTO mapCreateToDTO(CreateClassSessionDTO createClassSessionDTO) {
        return modelMapper.map(createClassSessionDTO, ClassSessionDTO.class);
    }
}
