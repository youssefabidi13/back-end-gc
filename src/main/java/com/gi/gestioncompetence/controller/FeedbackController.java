package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.dto.FeedbackDto;
import com.gi.gestioncompetence.entity.Feedback;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.FeedbackRepo;
import com.gi.gestioncompetence.repository.FileRepository;
import com.gi.gestioncompetence.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackRepo feedbackRepo;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public FeedbackController(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Feedback> addFeedback(@PathVariable Long id,@RequestBody FeedbackDto feedbackDto){

        Feedback feedback = new Feedback();
        feedback.setMessageFeedback(feedbackDto.getMessage_feedback());
        feedback.setRate(feedbackDto.getRate());
        feedback.setFormationHistory(fileRepository.findById(id).get());
        feedback.setUtilisateur(userRepo.findById(feedbackDto.getUtilisateur_id()).get());
        Feedback savedFeedback = feedbackRepo.save(feedback);

        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Iterable<Feedback>> getAllFeedback(){
        Iterable<Feedback> feedbacks = feedbackRepo.findAll();
        return ResponseEntity.ok(feedbacks);
    }


}