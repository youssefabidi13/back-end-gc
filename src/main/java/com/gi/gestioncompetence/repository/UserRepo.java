package com.gi.gestioncompetence.repository;

import com.gi.gestioncompetence.entity.UserFisca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserFisca, Long> {
    Optional<UserFisca> findByEmail(String email);

    boolean existsByEmail(String email);
    @Query("SELECT COUNT(u) FROM UserFisca u WHERE u.departement.idDepartement = :departementId")
    long countUsersByDepartementId(Long departementId);

    @Query("SELECT c.nomCompetence, COUNT(u) FROM UserFisca u JOIN u.competencesAcquises c GROUP BY c.nomCompetence")
    List<Object[]> countUsersByCompetence();


    Optional<UserFisca> findById(Long id);


    @Query("SELECT u FROM UserFisca u JOIN FETCH u.departement")
    List<UserFisca> findAllWithDepartments();
}
