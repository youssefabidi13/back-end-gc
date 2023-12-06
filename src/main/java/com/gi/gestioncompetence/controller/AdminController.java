package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.dto.CompetenceUserCountDTO;
import com.gi.gestioncompetence.dto.DepartmentEmployeeCountDTO;
import com.gi.gestioncompetence.dto.FeedbackDto;
import com.gi.gestioncompetence.entity.Feedback;
import com.gi.gestioncompetence.repository.*;
import com.gi.gestioncompetence.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;



    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CompetenceRepo competenceRepo;

    @Autowired
    private DepartementRepo departementRepo;

    @Autowired
    private FormationRepo formationRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;

    @GetMapping("/number-users")
    public long getNumberUsers() {
        return userRepo.count();
    }

    @GetMapping("/number-competences")
    public long getNumberCompetences() {
        return competenceRepo.count();
    }

    @GetMapping("/number-departments")
    public long getNumberDepartements() {
        return departementRepo.count();
    }

    @GetMapping("/number-formations")
    public long getNumberFormations() {
        return fileRepository.count();
    }

    @GetMapping("/all-rating")
    public List<Feedback> getAllRating() {
        List<Feedback> data = feedbackRepo.findAll();
        return data;
    }

    @GetMapping("/all-rating-numbers")
    public Map<String, String> getAllRatingNumber() {
        List<Integer> rates = feedbackRepo.findAllRate();

        Map<String, Integer> rateCountMap = new HashMap<>();
        for (Integer rate : rates) {
            rateCountMap.put(rate.toString(), rateCountMap.getOrDefault(rate.toString(), 0) + 1);
        }

        Map<String, String> rateDistribution = new HashMap<>();
        int totalFeedbacks = rates.size();
        for (Map.Entry<String, Integer> entry : rateCountMap.entrySet()) {
            int count = entry.getValue();
            double percentage = (count * 100.0) / totalFeedbacks;
            rateDistribution.put("rate " + entry.getKey(), String.format("%.2f%%", percentage));
        }

        return rateDistribution;

    }



    @GetMapping("/employeesByDepartment")
    public ResponseEntity<List<DepartmentEmployeeCountDTO>> getNumberOfEmployeesByDepartment() {
        List<DepartmentEmployeeCountDTO> data = adminService.getNumberOfEmployeesByDepartment();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/usersByCompetence")
    public ResponseEntity<List<CompetenceUserCountDTO>> getUsersByCompetence() {
        List<CompetenceUserCountDTO> data = adminService.getUsersByCompetence();
        return ResponseEntity.ok(data);
    }

}
