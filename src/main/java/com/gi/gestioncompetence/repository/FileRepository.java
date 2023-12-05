package com.gi.gestioncompetence.repository;


import com.gi.gestioncompetence.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface FileRepository extends JpaRepository<FileEntity,Long> {
    List<FileEntity> findByDepartment(String department);
}
