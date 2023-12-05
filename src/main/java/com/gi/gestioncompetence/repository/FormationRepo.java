package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.FormationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepo extends JpaRepository<FormationHistory, Long> {
}
