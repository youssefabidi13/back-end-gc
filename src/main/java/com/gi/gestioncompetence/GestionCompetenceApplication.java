package com.gi.gestioncompetence;

import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.UserRepo;
import com.gi.gestioncompetence.service.UserDataInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestionCompetenceApplication {

  @Autowired
  private UserRepo userRepo;
  @Autowired
  private UserDataInitializationService userDataInitializationService;


  @Autowired
  private PasswordEncoder passwordEncoder;
  public static void main(String[] args) {
    SpringApplication.run(GestionCompetenceApplication.class, args);
  }
//  @Bean
//  CommandLineRunner commandLineRunner() {
//    return args -> userDataInitializationService.initializeUserData();
//  }

//  @Bean
//  CommandLineRunner commandLineRunner() {
//    return args -> userDataInitializationService.initializeCompetences();
//  }

}
