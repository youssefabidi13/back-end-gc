package com.gi.gestioncompetence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Resignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;

    @Temporal(TemporalType.DATE)
    private Date departureDate;

    @Temporal(TemporalType.DATE)
    private Date requestDate;

    @Enumerated(EnumType.STRING)
    private ResignationStatus status;


    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UserFisca utilisateur;

}
