package com.gi.gestioncompetence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Competence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idCompetence;

  private String nomCompetence;
  private String descriptionCompetence;
  private String categorieDomaine;
  private String niveauCompetence;

}
