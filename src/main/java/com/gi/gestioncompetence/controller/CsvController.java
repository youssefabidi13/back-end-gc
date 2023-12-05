package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.service.UserFiscaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class CsvController {
    private final UserFiscaService userFiscaService;

    @GetMapping
    public List<UserFisca> getAllUserFiscas() {
        return userFiscaService.getAllUserFiscas();
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadUsers (
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(userFiscaService.uploadUsers(file));
    }
}
