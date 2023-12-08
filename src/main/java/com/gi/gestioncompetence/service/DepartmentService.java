package com.gi.gestioncompetence.service;

import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.repository.DepartementRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private DepartementRepo departmentRepository;


    public DepartmentService(DepartementRepo departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(departments::add);
        return departments;
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public void addDepartment(Department department) {
        departmentRepository.save(department);
    }

    public void update(Department updatedDepartment, Long id) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);

        if (existingDepartment.isPresent()) {
            Department department = existingDepartment.get();
            // Mettez à jour les propriétés nécessaires du département existant avec les nouvelles valeurs
            department.setNomDepartement(updatedDepartment.getNomDepartement());
            department.setDescriptionDepartement(updatedDepartment.getDescriptionDepartement());

            // Enregistrez la mise à jour dans la base de données
            departmentRepository.save(department);
        }
    }

    public boolean doesNameExist(String code) {
        return departmentRepository.existsByNomDepartement(code);
    }
    public Optional<Department> getDepartmentName(String name) {
        return departmentRepository.findByNomDepartement(name);
    }

}
