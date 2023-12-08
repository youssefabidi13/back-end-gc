package com.gi.gestioncompetence.dto;

import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.entity.UserFisca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDto {

    private Long idDepartement;

    private String nomDepartement;
    private String descriptionDepartement;

    public DepartementDto(Department department) {
        this.idDepartement = department.getIdDepartement();
        this.nomDepartement = department.getNomDepartement();
        this.descriptionDepartement = department.getDescriptionDepartement();


    }
}
