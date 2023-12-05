package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    @Query("SELECT f.rate FROM Feedback f")
    List<Integer> findAllRate();

}