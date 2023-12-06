package com.gi.gestioncompetence.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "department_name")
    private String department;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "file")
    private byte[] data;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserFisca userFisca;


    @JsonManagedReference
    @OneToMany(cascade=CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "formationHistory")
    private List<Feedback> feedbacks;


}

