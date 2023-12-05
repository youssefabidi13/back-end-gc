package com.gi.gestioncompetence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormationHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idFormationHistory;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
  @JoinColumn(name = "utilisateur_id")
  private UserFisca utilisateur;

  private String nomFormation;
  private Date dateDebut;
  private Date dateFin;

  @Transient
  public String getUtilisateurName() {
    return utilisateur != null ? utilisateur.getNomComplet() : null;
  }
}
