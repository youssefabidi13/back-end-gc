package com.gi.gestioncompetence.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDepDto {
    private Long idUtilisateur;
    private String nomComplet;
    private String email;
    private String password;
    private String role;

    private Long departementId ;

    private String departementName;
}



