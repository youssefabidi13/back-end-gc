package com.gi.gestioncompetence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idFeedback;


  @JsonBackReference
  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
  @JoinColumn(name = "file_id")
  private FileEntity formationHistory;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
  @JoinColumn(name = "utilisateur_id")
  private UserFisca utilisateur;

  private String messageFeedback;

  private int rate;

  private Date dateFeedback;

  @PrePersist
  public void onPrePersist() {
    dateFeedback = new Date();
  }
}