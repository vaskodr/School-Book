package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.director.dto.UpdateDirectorDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DirectorDTO createDirector(CreateDirectorDTO createDirectorDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(createDirectorDTO.getFirstName());
        userEntity.setLastName(createDirectorDTO.getLastName());
        userEntity.setDateOfBirth(createDirectorDTO.getBirthDate());
        userEntity.setGender(createDirectorDTO.getGender());
        userEntity.setPhone(createDirectorDTO.getPhone());
        userEntity.setEmail(createDirectorDTO.getEmail());
        userEntity.setUsername(createDirectorDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(createDirectorDTO.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);

        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setUserEntity(savedUser);

        directorEntity = directorRepository.save(directorEntity);

        return new DirectorDTO(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getDateOfBirth(), savedUser.getGender(), savedUser.getPhone(), savedUser.getEmail(), savedUser.getUsername());
    }

    @Override
    public DirectorDTO getDirectorById(Long id) {
        DirectorEntity directorEntity = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));
        UserEntity userEntity = directorEntity.getUserEntity();
        return new DirectorDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername());
    }

    @Override
    public List<DirectorDTO> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(directorEntity -> {
                    UserEntity userEntity = directorEntity.getUserEntity();
                    return new DirectorDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername());
                })
                .collect(Collectors.toList());
    }

    @Override
    public DirectorDTO updateDirector(Long id, UpdateDirectorDTO updateDirectorDTO) {
        DirectorEntity directorEntity = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));

        UserEntity userEntity = directorEntity.getUserEntity();
        if (updateDirectorDTO.getFirstName() != null) userEntity.setFirstName(updateDirectorDTO.getFirstName());
        if (updateDirectorDTO.getLastName() != null) userEntity.setLastName(updateDirectorDTO.getLastName());
        if (updateDirectorDTO.getDateOfBirth() != null) userEntity.setDateOfBirth(updateDirectorDTO.getDateOfBirth());
        if (updateDirectorDTO.getGender() != null) userEntity.setGender(updateDirectorDTO.getGender());
        if (updateDirectorDTO.getPhone() != null) userEntity.setPhone(updateDirectorDTO.getPhone());
        if (updateDirectorDTO.getEmail() != null) userEntity.setEmail(updateDirectorDTO.getEmail());
        if (updateDirectorDTO.getUsername() != null) userEntity.setUsername(updateDirectorDTO.getUsername());
        if (updateDirectorDTO.getPassword() != null) userEntity.setPassword(passwordEncoder.encode(updateDirectorDTO.getPassword()));

        userRepository.save(userEntity);

        return new DirectorDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getDateOfBirth(), userEntity.getGender(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getUsername());
    }

    @Override
    public void deleteDirector(Long id) {
        DirectorEntity directorEntity = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));
        directorRepository.deleteById(id);
    }
}