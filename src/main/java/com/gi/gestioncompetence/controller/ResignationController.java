package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.dto.ResignationDTO;
import com.gi.gestioncompetence.dto.ResignationRequest;
import com.gi.gestioncompetence.entity.Resignation;
import com.gi.gestioncompetence.repository.ResignationRepository;
import com.gi.gestioncompetence.service.ResignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ResignationController {

    private final ResignationService resignationService;
    private final ResignationRepository resignationRepository;

    @Autowired
    public ResignationController(ResignationService resignationService, ResignationRepository resignationRepository) {
        this.resignationService = resignationService;
        this.resignationRepository = resignationRepository;
    }

    @PostMapping("/api/users/{userId}/resignation")
    public ResponseEntity<Resignation> submitResignation(@PathVariable Long userId, @RequestBody ResignationRequest resignationRequest) throws ParseException, ParseException {
        // Parse the departureDate from the request
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDepartureDate = dateFormat.parse(resignationRequest.getDepartureDate());
        System.out.println(parsedDepartureDate);


        Resignation resignation = new Resignation();
        resignation.setReason(resignationRequest.getReason());
        resignation.setDepartureDate(parsedDepartureDate);
        resignation.setRequestDate(new Date());

        Resignation savedResignation = resignationService.submitResignation(userId, resignation);

        return new ResponseEntity<>(savedResignation, HttpStatus.CREATED);
    }


    @GetMapping("/api/admin/resignations")
    public ResponseEntity<List<ResignationDTO>> getPendingResignationsWithUserInfo() {
        List<ResignationDTO> pendingResignations = resignationService.getPendingResignationsWithUserInfo();
        return new ResponseEntity<>(pendingResignations, HttpStatus.OK);
    }


    @PostMapping("/api/admin/{resignationId}/approve")
    public ResponseEntity<String> approveResignation(@PathVariable("resignationId") Long resignationId) {
        resignationService.approveResignation(resignationId);
        return ResponseEntity.ok("Resignation Approved");
    }


    @PutMapping("/api/admin/{resignationId}/reject")
    public ResponseEntity<String> rejectResignation(@PathVariable Long resignationId) {
        resignationService.rejectResignation(resignationId);
        return ResponseEntity.ok("Resignation Rejected");
    }

}


