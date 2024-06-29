package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.enums.Gender;
import com.nbu.schoolbook.exception.ResourceNotFoundException;
import com.nbu.schoolbook.role.RoleEntity;
import com.nbu.schoolbook.role.RoleRepository;
import com.nbu.schoolbook.school.SchoolEntity;
import com.nbu.schoolbook.school.SchoolRepository;
import com.nbu.schoolbook.user.UserEntity;
import com.nbu.schoolbook.user.UserMapper;
import com.nbu.schoolbook.user.UserRepository;
import com.nbu.schoolbook.user.UserService;
import com.nbu.schoolbook.user.director.dto.CreateDirectorDTO;
import com.nbu.schoolbook.user.director.dto.DirectorDTO;
import com.nbu.schoolbook.user.director.dto.UpdateDirectorDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final SchoolRepository schoolRepository;
    private final DirectorMapper directorMapper;
    private final UserMapper userMapper;

    @Override
    public DirectorDTO registerDirector(RegisterDTO registerDTO, Long schoolId) {
        UserEntity user = getUser(registerDTO, "ROLE_DIRECTOR");
        SchoolEntity school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        DirectorEntity director = createDirectorEntity(user, school);
        return directorMapper.mapToDTO(director);
    }

    @Override
    public DirectorDTO getDirectorById(Long id) {
        DirectorEntity directorEntity = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));
        return directorMapper.mapToDTO(directorEntity);
    }

    @Override
    public List<DirectorDTO> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(directorMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DirectorDTO updateDirector(Long id, UpdateDirectorDTO updateDirectorDTO) {
        DirectorEntity directorEntity = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));

        updateUserEntity(directorEntity.getUserEntity(), updateDirectorDTO);
        userRepository.save(directorEntity.getUserEntity());

        if (updateDirectorDTO.getSchoolId() != null) {
            SchoolEntity school = schoolRepository.findById(updateDirectorDTO.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));
            directorEntity.setSchool(school);
        }

        directorRepository.save(directorEntity);
        return directorMapper.mapToDTO(directorEntity);
    }

    @Override
    public void deleteDirector(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Director not found");
        }
        directorRepository.deleteById(id);
    }

    @Override
    public void assignDirectorToSchool(Long directorId, Long schoolId) {
        DirectorEntity director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));
        SchoolEntity school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        director.setSchool(school);
        directorRepository.save(director);
        school.setDirector(director);
        schoolRepository.save(school);
    }

    @Override
    public void unassignDirectorFromSchool(Long directorId, Long schoolId) {
        DirectorEntity director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));
        SchoolEntity school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));


        director.setSchool(null);
        directorRepository.save(director);

        school.setDirector(null);
        schoolRepository.save(school);


    }

    private UserEntity getUser(RegisterDTO registerDTO, String roleName) {
        Optional<UserEntity> userOptional = userRepository.findById(registerDTO.getId());
        RoleEntity role = roleRepository.findByName(roleName);

        if (role == null) {
            throw new RuntimeException(roleName + " role not found!");
        }

        UserEntity user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getRoles().contains(role) && directorRepository.existsByUserEntity(user)) {
                throw new RuntimeException("User is already a " + roleName.toLowerCase() + "!");
            }
            user.getRoles().add(role);
        } else {
            user = userMapper.mapRegisterDTOToEntity(registerDTO);
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
        }
        return userRepository.save(user);
    }

    private DirectorEntity createDirectorEntity(UserEntity user, SchoolEntity school) {
        DirectorEntity director = new DirectorEntity();
        director.setUserEntity(user);
        director.setSchool(school);
        return directorRepository.save(director);
    }

    private void updateUserEntity(UserEntity userEntity, UpdateDirectorDTO updateDirectorDTO) {
        if (updateDirectorDTO.getFirstName() != null) userEntity.setFirstName(updateDirectorDTO.getFirstName());
        if (updateDirectorDTO.getLastName() != null) userEntity.setLastName(updateDirectorDTO.getLastName());
        if (updateDirectorDTO.getDateOfBirth() != null) userEntity.setDateOfBirth(updateDirectorDTO.getDateOfBirth());
        if (updateDirectorDTO.getGender() != null) userEntity.setGender(updateDirectorDTO.getGender());
        if (updateDirectorDTO.getPhone() != null) userEntity.setPhone(updateDirectorDTO.getPhone());
        if (updateDirectorDTO.getEmail() != null) userEntity.setEmail(updateDirectorDTO.getEmail());
        if (updateDirectorDTO.getUsername() != null) userEntity.setUsername(updateDirectorDTO.getUsername());
        if (updateDirectorDTO.getPassword() != null)
            userEntity.setPassword(passwordEncoder.encode(updateDirectorDTO.getPassword()));
    }
}