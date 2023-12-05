package com.gi.gestioncompetence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private String message_feedback;
    private int rate;
    private long file_id;
    private long utilisateur_id;
}
