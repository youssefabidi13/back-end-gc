package com.gi.gestioncompetence.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gi.gestioncompetence.entity.Feedback;
import com.gi.gestioncompetence.entity.UserFisca;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private Long id;


    private String filename;


    private String department;


    private String contentType;


    private byte[] data;


    private String fullName;



    private List<FeedBackDtoOriginal> feedbacks =new ArrayList<>();



    public void setFeedback(String messageFeedback, int rate, long fileId, String name, Date dateFeedback) {
        this.feedbacks.add(new FeedBackDtoOriginal(messageFeedback, rate, fileId, name, dateFeedback));

    }
}
