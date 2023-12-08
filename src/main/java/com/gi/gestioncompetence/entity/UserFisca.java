package com.gi.gestioncompetence.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFisca implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idUtilisateur;

  private String nomComplet;
  @Column(unique = true)
  private String email;
  private String password;
  private String role;

  @Transient
  private Long departementId ;

  @Transient
  private String decodedPassword ;

  @JsonManagedReference
  @OneToMany(cascade=CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "id")
  private List<FileEntity> fileEntities;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
  @JoinColumn(name = "departement_id")
  private Department departement;

  @ManyToMany
  @JoinTable(name = "user_competence",
    joinColumns = @JoinColumn(name = "utilisateur_id"),
    inverseJoinColumns = @JoinColumn(name = "competence_id"))
  private List<Competence> competencesAcquises;


  @JsonManagedReference
  @OneToMany(cascade=CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "utilisateur")
  private List<FormationHistory> historiqueFormation;

  @JsonManagedReference
  @OneToMany(cascade=CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "utilisateur")
  private List<Feedback> feedbacks;

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY, mappedBy = "utilisateur")
  private List<Resignation> resignations;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = Arrays.stream(role.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
