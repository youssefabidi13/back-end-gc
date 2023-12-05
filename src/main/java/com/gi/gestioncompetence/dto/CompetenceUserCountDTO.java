package com.gi.gestioncompetence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CompetenceUserCountDTO {
    private String competenceName;
    private long userCount;
}
