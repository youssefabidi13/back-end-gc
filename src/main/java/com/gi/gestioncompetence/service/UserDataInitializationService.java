package com.gi.gestioncompetence.service;


import com.gi.gestioncompetence.entity.Competence;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.CompetenceRepo;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDataInitializationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final DepartementRepo departementRepo;
    private final CompetenceRepo competenceRepo;


    @Autowired
    public UserDataInitializationService(UserRepo userRepo, PasswordEncoder passwordEncoder,
                                         DepartementRepo departementRepo, CompetenceRepo competenceRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.departementRepo = departementRepo;
        this.competenceRepo = competenceRepo;
    }

    public void initializeUserData() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                String email = "user" + (i * 9 + j) + "@example.com";
                userRepo.save(UserFisca.builder()
                        .email(email)
                        .nomComplet("User " + (i * 9 + j))
                        .role("USER")
                        .password(passwordEncoder.encode("123456"))
                        // Set department ID based on your logic
                        .departement(departementRepo.findById((long) i).orElseThrow())
                        .build());
            }
        }
    }

    public void initializeCompetences() {
        initializeCompetency("Java", "Java programming language", "Info", "Intermediate");
        initializeCompetency("Data Science", "Data Science techniques", "Info", "Advanced");
        initializeCompetency("Machine Learning", "Machine Learning algorithms", "Info", "Expert");
        initializeCompetency("Big Data", "Big Data processing", "Info", "Intermediate");
        initializeCompetency("Photoshop", "Image editing using Photoshop", "Info", "Beginner");

        initializeCompetency("Networking Basics", "Basic networking concepts", "Réseau", "Beginner");
        initializeCompetency("Network Security", "Securing computer networks", "Réseau", "Intermediate");
        initializeCompetency("Routing and Switching", "Routing and switching technologies", "Réseau", "Advanced");
        initializeCompetency("Wireless Networking", "Wireless network technologies", "Réseau", "Intermediate");
        initializeCompetency("Network Troubleshooting", "Troubleshooting network issues", "Réseau", "Intermediate");

        initializeCompetency("Supply Chain Fundamentals", "Basic concepts of supply chain management", "SupplyChain", "Beginner");
        initializeCompetency("Inventory Management", "Managing inventory in supply chain", "SupplyChain", "Intermediate");
        initializeCompetency("Demand Forecasting", "Forecasting demand for products", "SupplyChain", "Advanced");
        initializeCompetency("Logistics Optimization", "Optimizing logistics operations", "SupplyChain", "Advanced");
        initializeCompetency("Supply Chain Analytics", "Analyzing supply chain data", "SupplyChain", "Intermediate");

        initializeCompetency("Mechatronics Basics", "Basic principles of mechatronics", "Mecatronique", "Beginner");
        initializeCompetency("Robotics", "Design and control of robots", "Mecatronique", "Advanced");
        initializeCompetency("Control Systems", "Control systems engineering", "Mecatronique", "Intermediate");
        initializeCompetency("Embedded Systems", "Designing embedded systems", "Mecatronique", "Intermediate");
        initializeCompetency("Sensor Technology", "Understanding sensor technologies", "Mecatronique", "Intermediate");

        initializeCompetency("Civil Engineering Fundamentals", "Basic concepts in civil engineering", "Civil", "Beginner");
        initializeCompetency("Structural Analysis", "Analyzing structures", "Civil", "Intermediate");
        initializeCompetency("Geotechnical Engineering", "Understanding soil mechanics", "Civil", "Intermediate");
        initializeCompetency("Transportation Engineering", "Designing transportation systems", "Civil", "Advanced");
        initializeCompetency("Environmental Engineering", "Addressing environmental issues", "Civil", "Intermediate");
    }


    private void initializeCompetency(String nom, String description, String categorieDomaine, String niveau) {
        competenceRepo.save(Competence.builder()
                .nomCompetence(nom)
                .descriptionCompetence(description)
                .categorieDomaine(categorieDomaine)
                .niveauCompetence(niveau)
                .build());
    }
}
