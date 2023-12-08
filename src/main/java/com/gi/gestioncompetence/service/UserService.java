package com.gi.gestioncompetence.service;

import com.gi.gestioncompetence.dto.UserDepDto;
import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private UserRepo userRepository;
    private DepartementRepo departmentRepository;

    public UserService(UserRepo userRepository, DepartementRepo departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }
    public void addUser(UserFisca user) {
        // Set the department on the user

        System.out.println("Received UserFisca: " + user);
        Department userDepartment = user.getDepartement();
        if (userDepartment != null) {
            Department department = departmentRepository.findById(userDepartment.getIdDepartement()).orElse(null);
            user.setDepartement(department);
        }

        // Save the user
        userRepository.save(user);

        // Add the user to the users list in the department
        if (user.getDepartement() != null) {
            Department department = user.getDepartement();
            List<UserFisca> users = department.getMembresDepartement();
            if (users == null) {
                users = new ArrayList<>();
            }
            users.add(user);
            department.setMembresDepartement(users);
            departmentRepository.save(department);
        }
    }
    public List<UserDepDto> getUsers() {
        List<UserDepDto> usersDto = new ArrayList<>();
        List<UserFisca> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        for (UserFisca user : users) {
            UserDepDto userDto = new UserDepDto();
            userDto.setIdUtilisateur(user.getIdUtilisateur());
            userDto.setNomComplet(user.getNomComplet());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setRole(user.getRole());
            userDto.setDepartementId(user.getDepartement().getIdDepartement());
            userDto.setDepartementName(user.getDepartement().getNomDepartement());
            usersDto.add(userDto);
        }

        return usersDto;
    }

    // UserService.java
    public List<UserFisca> getUsersWithDepartments() {
        return userRepository.findAllWithDepartments();
    }


    public UserFisca getUser(Long id) {
        return userRepository.findById((long) id).orElse(null);
    }

    public void deleteUser(int id) {
        userRepository.deleteById((long) id);
    }


    public ResponseEntity<String> update(UserFisca updatedUser, int id) {
        // Récupérer l'utilisateur existant par son ID
        Optional<UserFisca> existingUserOptional = userRepository.findById((long) id);

        if (existingUserOptional.isPresent()) {
            UserFisca existingUser = existingUserOptional.get();

            // Vérifier si l'e-mail a été modifié
            if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
                // L'e-mail a été modifié, vérifier s'il est unique
                if (doesEmailExist(updatedUser.getEmail())) {
                    return new ResponseEntity<>("L'e-mail existe déjà pour un autre utilisateur", HttpStatus.CONFLICT);
                }
            }

            // Mettez à jour les propriétés nécessaires de l'utilisateur existant avec les nouvelles valeurs
            existingUser.setNomComplet(updatedUser.getNomComplet());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setDepartement(updatedUser.getDepartement());

            // Enregistrez la mise à jour dans la base de données
            userRepository.save(existingUser);

            return new ResponseEntity<>("Utilisateur mis à jour avec succès", HttpStatus.OK);
        } else {
            // L'utilisateur avec l'ID spécifié n'a pas été trouvé
            return new ResponseEntity<>("Utilisateur non trouvé", HttpStatus.NOT_FOUND);
        }
    }


    public void update2(UserFisca updatedUser, int id) {
        Optional<UserFisca> existingUser = userRepository.findById((long) id);

        if (existingUser.isPresent()) {
            UserFisca user = existingUser.get();
            // Mettez à jour les propriétés nécessaires du département existant avec les nouvelles valeurs
            user.setNomComplet(updatedUser.getNomComplet());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());


            // Enregistrez la mise à jour dans la base de données
            userRepository.save(user);
        }
    }

    public boolean doesEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }
    public Optional<UserFisca> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();

    }
}

