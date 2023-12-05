package com.gi.gestioncompetence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idDepartement;

  private String nomDepartement;
  private String descriptionDepartement;

  @OneToOne
  private UserFisca responsableDepartement;

  @JsonManagedReference
  @OneToMany(cascade=CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "departement")
  private List<UserFisca> membresDepartement;
}
