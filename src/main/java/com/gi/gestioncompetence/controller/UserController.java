package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableMethodSecurity(prePostEnabled = true)
@CrossOrigin("*")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartementRepo departementRepo;

    @GetMapping("/auth/departements")
    public List<Department> getDepartements() {
        return departementRepo.findAll();
    }

    @PostMapping("/auth/login")
    public Map<String,String> login (String email, String password){
        System.out.println(email+" - "+password);
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        Instant instant=Instant.now();
        String scope = authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(email)
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters=JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(),jwtClaimsSet);
        String jwt=jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        System.out.println(jwt);
        return Map.of("access-token",jwt);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody UserFisca user) {
        try {
            // Check if the email is already taken
            if (userRepo.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Email is already taken", HttpStatus.CONFLICT);
            }

            System.out.println(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user.getDepartementId());

            user.setDepartement(departementRepo.findById(user.getDepartementId()).get());
            System.out.println(user.getDepartement().getIdDepartement());

            userRepo.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth/userId/{email}")
    public ResponseEntity<Long> getUserId(@PathVariable String email) {
        try {
            Optional<UserFisca> user = userRepo.findByEmail(email);
            if (user.isPresent()) {
                return new ResponseEntity<>(user.get().getIdUtilisateur(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

