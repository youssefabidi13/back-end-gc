package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.dto.DepartementDto;
import com.gi.gestioncompetence.dto.UserDepDto;
import com.gi.gestioncompetence.dto.UserDto;
import com.gi.gestioncompetence.dto.UserDto1;
import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.UserRepo;
import com.gi.gestioncompetence.service.UserService;
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

    private  DepartementRepo departmentRepository;
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Autowired  // Add this annotation
    public UserController(UserService userService, DepartementRepo departmentRepository) {  // Add DepartmentRepository as a parameter
        this.userService = userService;
        this.departmentRepository = departmentRepository;  // Add this line
    }
    @GetMapping("/auth/departements")
    public List<DepartementDto> getDepartements() {
        return departmentRepository.findAll().stream().map(DepartementDto::new).collect(Collectors.toList());
    }

    @GetMapping("/auth/user/fullname/{id}")
    public String getUserFullName(@PathVariable Long id) {
        Optional<UserFisca> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get().getNomComplet();
        } else {
            return "";
        }
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
    public ResponseEntity<String> registerUser(@RequestBody UserDto user) {
        try {
            // Check if the email is already taken
            if (userRepo.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>("Email is already taken", HttpStatus.CONFLICT);
            }

            System.out.println(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user.getDepartementId());

            UserFisca userFisca = new UserFisca();
            userFisca.setNomComplet(user.getNomComplet());
            userFisca.setEmail(user.getEmail());
            userFisca.setPassword(user.getPassword());
            userFisca.setRole(user.getRole());
            userFisca.setDepartementId(user.getDepartementId());
            userFisca.setDepartement(departementRepo.findById(user.getDepartementId()).get());

            userRepo.save(userFisca);
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

    @RequestMapping(method = RequestMethod.GET, value = "/api/Users2")
    public List<UserDepDto> getUsers() {
        return userService.getUsers();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/api/Users")
    public List<UserFisca> getUsersWithDepartments() {
        return userService.getUsersWithDepartments();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/api/User/{id}")
    public UserFisca getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/departmentsName")
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = userService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/deleteUser/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/newUser")
    public ResponseEntity<String> addUser(@RequestBody UserFisca user) {
        // Vérifiez d'abord si l'email 'existe
        if (userService.doesEmailExist(user.getEmail())) {
            return new ResponseEntity<>("L'email de l'user existe déjà", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Si le code n'existe pas, ajoutez le département
        userService.addUser(user);
        return new ResponseEntity<>("User créé avec succès", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/api/updateUser/{id}/{departement_id}")
    public ResponseEntity<String> updateUser(@RequestBody UserDto1 user, @PathVariable Long id,@PathVariable Long departement_id) {
        UserFisca userFisca = new UserFisca();
        userFisca.setIdUtilisateur(id);
        userFisca.setNomComplet(user.getNomComplet());
        userFisca.setEmail(user.getEmail());
        userFisca.setDepartementId(departement_id);
       System.out.println(departement_id+"hello");
        Department department = departementRepo.findById(userFisca.getDepartementId()).orElse(null);

        userFisca.setDepartement(department);

        userService.update(userFisca, Math.toIntExact(id));
        return new ResponseEntity<>("user mis à jour avec succès", HttpStatus.OK);


    }


//    @RequestMapping(method = RequestMethod.PUT, value = "/api/updateUser2/{id}")
//    public ResponseEntity<String> updateUser(@RequestBody UserFisca user, @PathVariable int id) {
//        // Vérifiez si le code de département existe déjà pour un autre département
//        Optional<UserFisca> existingUserOptional = userService.getUserByEmail(user.getEmail());
//
//        if (existingUserOptional.isPresent() && existingUserOptional.get().getIdUtilisateur() != id) {
//            return new ResponseEntity<>("L'email existe déjà pour un autre utilisateur", HttpStatus.CONFLICT);
//        }
//
//        // Mise à jour de l'utilisateur
//        userService.update(user, id);
//        return new ResponseEntity<>("Utilisateur mis à jour avec succès", HttpStatus.OK);
//    }



}

