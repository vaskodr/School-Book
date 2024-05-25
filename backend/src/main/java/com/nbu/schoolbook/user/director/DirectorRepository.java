package com.nbu.schoolbook.user.director;

import com.nbu.schoolbook.user.director.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {
}
