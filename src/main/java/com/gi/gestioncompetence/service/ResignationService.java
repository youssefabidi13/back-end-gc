package com.gi.gestioncompetence.service;

import com.gi.gestioncompetence.dto.ResignationDTO;
import com.gi.gestioncompetence.entity.Resignation;
import com.gi.gestioncompetence.entity.ResignationStatus;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.ResignationRepository;
import com.gi.gestioncompetence.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResignationService {
    private final ResignationRepository resignationRepository;
    private final UserRepo userRepository;
    private final EmailServiceResignation emailService;

    @Autowired
    public ResignationService(ResignationRepository resignationRepository, UserRepo userRepository, EmailServiceResignation emailService) {
        this.resignationRepository = resignationRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public Resignation submitResignation(Long userId, Resignation resignation) {
        UserFisca user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        resignation.setUtilisateur(user);
        resignation.setStatus(ResignationStatus.PENDING);
        Resignation savedResignation = resignationRepository.save(resignation);

        // Send resignation notification email
        sendResignationNotification(savedResignation);

        return savedResignation;
    }

    private void sendResignationNotification(Resignation resignation) {
        String to = resignation.getUtilisateur().getEmail();
        String subject = "Resignation Submitted";
        String body = "Your resignation request has been submitted.\nReason: " + resignation.getReason();
        emailService.sendEmail(to, subject, body);
    }

    public void approveResignation(Long resignationId) {
        Resignation resignation = resignationRepository.findById(resignationId)
                .orElseThrow(() -> new RuntimeException("Resignation not found with id: " + resignationId));

        Long userId = resignation.getUtilisateur().getIdUtilisateur(); // Assuming 'Utilisateur' has an 'id' field
        resignation.setStatus(ResignationStatus.ACCEPTED);
        Resignation savedResignation = resignationRepository.save(resignation);

        // Send resignation response email
        sendResignationResponseApprove(savedResignation);
    }

    private void sendResignationResponse(Resignation resignation) {
        String to = resignation.getUtilisateur().getEmail();
        String subject = "Resignation Response";
        String body = "Your resignation request has been " + resignation.getStatus().toString().toLowerCase() + ".";
        emailService.sendResignationResponseEmail(to, subject, body);
    }
    private void sendResignationResponseApprove(Resignation resignation) {
        String to = resignation.getUtilisateur().getEmail();
        String subject = "Resignation Response";
        String body = "Your resignation request has been " + resignation.getStatus().toString().toLowerCase() + ". Please upload any course materials you have to the system.";
        emailService.sendResignationResponseEmail(to, subject, body);
    }
    public void rejectResignation(Long resignationId) {
        Resignation resignation = resignationRepository.findById(resignationId)
                .orElseThrow(() -> new RuntimeException("Resignation not found with id: " + resignationId));

        Long userId = resignation.getUtilisateur().getIdUtilisateur(); // Assuming 'Utilisateur' has an 'id' field
        resignation.setStatus(ResignationStatus.REJECTED);
        Resignation savedResignation = resignationRepository.save(resignation);

        // Send resignation response email
        sendResignationResponse(savedResignation);
    }


    public List<ResignationDTO> getPendingResignationsWithUserInfo() {
        List<Resignation> pendingResignations = resignationRepository.findByStatus(ResignationStatus.PENDING);

        List<ResignationDTO> resignationDTOList = new ArrayList<>();

        for (Resignation resignation : pendingResignations) {
            UserFisca user = resignation.getUtilisateur();
            String nomUtilisateur = user.getNomComplet();

            ResignationDTO resignationDTO = new ResignationDTO();
            resignationDTO.setResignationId(resignation.getId());
            resignationDTO.setNomUtilisateur(nomUtilisateur);
            resignationDTO.setReason(resignation.getReason());
            resignationDTO.setDepartureDate(resignation.getDepartureDate().toString());
            resignationDTO.setRequestDate(resignation.getRequestDate().toString());

            resignationDTOList.add(resignationDTO);
        }

        return resignationDTOList;
    }


}

