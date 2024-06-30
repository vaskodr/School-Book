package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import com.nbu.schoolbook.user.student.StudentMapper;
import com.nbu.schoolbook.user.student.dto.StudentDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ParentMapper {

    public ParentDTO mapToDTO(ParentEntity parent) {
        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setId(parent.getId());
        parentDTO.setFirstName(parent.getUserEntity().getFirstName());
        parentDTO.setLastName(parent.getUserEntity().getLastName());
        parentDTO.setDateOfBirth(parent.getUserEntity().getDateOfBirth());
        parentDTO.setGender(parent.getUserEntity().getGender());
        parentDTO.setPhone(parent.getUserEntity().getPhone());
        parentDTO.setEmail(parent.getUserEntity().getEmail());
        parentDTO.setUsername(parent.getUserEntity().getUsername());

        // ParentDTO no longer maps students directly here

        return parentDTO;
    }
}
