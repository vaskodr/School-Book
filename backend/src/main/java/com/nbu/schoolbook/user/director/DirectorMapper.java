package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DirectorMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public DirectorDTO mapToDTO(DirectorEntity director){
        UserEntity userEntity = userRepository.getReferenceById(director.getUserEntity().getId());
        return modelMapper.map(userEntity,DirectorDTO.class);
    }

    public DirectorEntity mapToEntity(DirectorDTO directorDTO) {
        return modelMapper.map(directorDTO, DirectorEntity.class);
    }

}
