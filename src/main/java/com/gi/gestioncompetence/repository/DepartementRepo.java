package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepo extends JpaRepository<Department, Long> {
    Optional<Department> findByNomDepartement(String nomDepartement);
    Optional<Department> findById(Long id);
    boolean existsByNomDepartement(String departementName);

}
