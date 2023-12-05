package com.gi.gestioncompetence.entity;


import jakarta.persistence.*;
import lombok.Data;

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


}

