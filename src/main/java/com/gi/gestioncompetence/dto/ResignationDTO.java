package com.gi.gestioncompetence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResignationDTO {
    private Long resignationId;
    private String nomUtilisateur;
    private String reason;
    private String departureDate;
    private String requestDate;
}
