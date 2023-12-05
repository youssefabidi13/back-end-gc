package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepo extends JpaRepository<Competence, Long> {

}
