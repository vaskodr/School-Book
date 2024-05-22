package com.nbu.schoolbook.repository.dao;

import com.nbu.schoolbook.director.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {
}
