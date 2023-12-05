package com.gi.gestioncompetence.service;

import com.gi.gestioncompetence.dto.CompetenceUserCountDTO;
import com.gi.gestioncompetence.dto.DepartmentEmployeeCountDTO;
import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepo userFiscaRepository;

    @Autowired
    private DepartementRepo departmentRepository;

    public List<DepartmentEmployeeCountDTO> getNumberOfEmployeesByDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentEmployeeCountDTO> result = new ArrayList<>();

        for (Department department : departments) {
            long employeeCount = userFiscaRepository.countUsersByDepartementId(department.getIdDepartement());
            result.add(new DepartmentEmployeeCountDTO(department.getNomDepartement(), employeeCount));
        }

        return result;
    }

    public List<CompetenceUserCountDTO> getUsersByCompetence() {
        List<Object[]> result = userFiscaRepository.countUsersByCompetence();
        return result.stream()
                .map(obj -> new CompetenceUserCountDTO((String) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }
}

