package com.nbu.schoolbook.class_session;
import com.nbu.schoolbook.class_session.dto.ClassSessionDTO;
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

}