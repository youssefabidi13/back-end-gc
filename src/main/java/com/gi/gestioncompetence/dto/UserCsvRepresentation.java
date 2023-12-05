package com.gi.gestioncompetence.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCsvRepresentation {

    @CsvBindByName(column = "nom_complet")
    private String nom_complet;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "role")
    private String role;

    @CsvBindByName(column = "departement_name")
    private String departement_name;

    @CsvBindByName(column = "password")
    private String password;


}

