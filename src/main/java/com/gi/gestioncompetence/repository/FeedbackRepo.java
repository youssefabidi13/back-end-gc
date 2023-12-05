package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
}