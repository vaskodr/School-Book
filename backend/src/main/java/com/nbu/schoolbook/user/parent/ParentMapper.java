package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ParentMapper {
    private ModelMapper modelMapper;

    public ParentDTO mapToDTO(ParentEntity parent) {
        return modelMapper.map(parent, ParentDTO.class);
    }
}
