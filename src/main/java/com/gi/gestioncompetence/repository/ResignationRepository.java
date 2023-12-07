package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.Resignation;
import com.gi.gestioncompetence.entity.ResignationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResignationRepository extends JpaRepository<Resignation, Long>
{
    List<Resignation> findByStatus(ResignationStatus status);
}