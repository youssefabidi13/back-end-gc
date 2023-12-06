package com.gi.gestioncompetence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackDtoOriginal {
    private String message_feedback;
    private int rate;
    private long file_id;
    private String name;
    private Date dateFeedback;
}
