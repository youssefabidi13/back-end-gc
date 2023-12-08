package com.gi.gestioncompetence.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String nomComplet;
    private String email;
    private String password;
    private String role;

    private Long departementId ;
}
