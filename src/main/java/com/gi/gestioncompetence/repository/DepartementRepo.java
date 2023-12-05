package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepo extends JpaRepository<Department, Long> {

}
