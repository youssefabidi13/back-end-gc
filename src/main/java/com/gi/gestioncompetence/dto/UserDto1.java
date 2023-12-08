package com.gi.gestioncompetence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto1 {

    private Long idUtilisateur;
    private String nomComplet;
    private String email;



    private Long departement_id ;


}
