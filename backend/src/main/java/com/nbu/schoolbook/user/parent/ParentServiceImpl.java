package com.nbu.schoolbook.user.parent;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.parent.dto.CreateParentDTO;
import com.nbu.schoolbook.user.parent.dto.ParentDTO;
import com.nbu.schoolbook.user.parent.dto.UpdateParentDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ParentDTO createParent(CreateParentDTO createParentDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(createParentDTO.getFirstName());
        userEntity.setLastName(createParentDTO.getLastName());
        userEntity.setDateOfBirth(createParentDTO.getBirthDate());
        userEntity.setGender(createParentDTO.getGender());
        userEntity.setPhone(createParentDTO.getPhone());
        userEntity.setEmail(createParentDTO.getEmail());
        userEntity.setUsername(createParentDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(createParentDTO.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);

        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setUserEntity(savedUser);

        parentEntity = parentRepository.save(parentEntity);

        return new ParentDTO(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getDateOfBirth(), savedUser.getGender(), savedUser.getPhone(), savedUser.getEmail(), savedUser.getUsername());
    }

    @Override
    public ParentDTO getParentById(Long id) {
        ParentEntity parentEntity = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
        UserEntity userEntity = parentEntity.getUserEntity();
        return new ParentDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername());
    }

    @Override
    public List<ParentDTO> getAllParents() {
        return parentRepository.findAll().stream()
                .map(parentEntity -> {
                    UserEntity userEntity = parentEntity.getUserEntity();
                    return new ParentDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername());
                })
                .collect(Collectors.toList());
    }

    @Override
    public ParentDTO updateParent(Long id, UpdateParentDTO updateParentDTO) {
        ParentEntity parentEntity = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        UserEntity userEntity = parentEntity.getUserEntity();
        if (updateParentDTO.getFirstName() != null) userEntity.setFirstName(updateParentDTO.getFirstName());
        if (updateParentDTO.getLastName() != null) userEntity.setLastName(updateParentDTO.getLastName());
        if (updateParentDTO.getDateOfBirth() != null) userEntity.setDateOfBirth(updateParentDTO.getDateOfBirth());
        if (updateParentDTO.getGender() != null) userEntity.setGender(updateParentDTO.getGender());
        if (updateParentDTO.getPhone() != null) userEntity.setPhone(updateParentDTO.getPhone());
        if (updateParentDTO.getEmail() != null) userEntity.setEmail(updateParentDTO.getEmail());
        if (updateParentDTO.getUsername() != null) userEntity.setUsername(updateParentDTO.getUsername());
        if (updateParentDTO.getPassword() != null)
            userEntity.setPassword(passwordEncoder.encode(updateParentDTO.getPassword()));

        userRepository.save(userEntity);

        return new ParentDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername());
    }

    @Override
    public void deleteParent(Long id) {
        ParentEntity parentEntity = parentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
        parentRepository.deleteById(id);
    }
}