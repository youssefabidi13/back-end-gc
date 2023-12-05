package com.gi.gestioncompetence.service;

import com.gi.gestioncompetence.dto.UserCsvRepresentation;
import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.UserRepo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFiscaService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepo userFiscaRepository;
    private final DepartementRepo departmentRepository;

    private final EmailSenderService emailSenderService;

    public List<UserFisca> getAllUserFiscas() {
        return userFiscaRepository.findAll();
    }


    public Integer uploadUsers(MultipartFile file) throws IOException {
        Set<UserFisca> users = parseCsv(file);
        // Send email to notify new users
        users.forEach(user -> {
            System.out.println(user.getDecodedPassword());
            String subject = "Welcome to the System";
            String body = "Hello " + user.getNomComplet() + ",\n\n"
                    + "You have been added to the system. Your password is: " + user.getDecodedPassword();

            emailSenderService.sendSimpleEmail(user.getEmail(), subject, body);
        });
        userFiscaRepository.saveAll(users);
        return users.size();
    }

    private Set<UserFisca> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<UserCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(UserCsvRepresentation.class);
            CsvToBean<UserCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<UserCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();


            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> {
                        Department department = departmentRepository.findByNomDepartement(csvLine.getDepartement_name())
                                .orElseThrow(() -> new RuntimeException("Department not found: " + csvLine.getDepartement_name()));

                        return UserFisca.builder()
                                .nomComplet(csvLine.getNom_complet())
                                .email(csvLine.getEmail())
                                .role(csvLine.getRole())
                                .departement(department)
                                .decodedPassword(csvLine.getPassword())
                                .password(passwordEncoder.encode(csvLine.getPassword()))
                                .build();
                    })
                    .collect(Collectors.toSet());

        }
    }
}
