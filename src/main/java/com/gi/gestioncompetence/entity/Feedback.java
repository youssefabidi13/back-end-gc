package com.gi.gestioncompetence.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idFeedback;

  @ManyToOne
  private UserFisca utilisateur;

  private String typeFeedback;
  private String messageFeedback;
  private Date dateFeedback;

}
