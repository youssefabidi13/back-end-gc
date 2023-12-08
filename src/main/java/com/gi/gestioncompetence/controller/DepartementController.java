package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.dto.DepartementDto;
import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DepartementController {

    private final DepartmentService departmentService;

    public DepartementController(DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Departments")
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Department/{id}")
    public Department getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletedepartment/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/newDepartment")
    public ResponseEntity<String> addDepartment(@RequestBody DepartementDto department) {
        // Vérifiez d'abord si le code de département existe
        if (departmentService.doesNameExist(department.getNomDepartement())) {
            return new ResponseEntity<>("Le code de département existe déjà", HttpStatus.CONFLICT);
        }
        Department department1 = new Department();
department1.setNomDepartement(department.getNomDepartement());
department1.setDescriptionDepartement(department.getDescriptionDepartement());

        // Si le code n'existe pas, ajoutez le département
        departmentService.addDepartment(department1);
        return new ResponseEntity<>("Département créé avec succès", HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/updatedepartment/{id}")
    public ResponseEntity<String> updateDepartment(@RequestBody DepartementDto department, @PathVariable Long id) {
        // Vérifiez si le code de département existe déjà pour un autre département
        Optional<Department> existingDepartment = departmentService.getDepartmentName(department.getNomDepartement());
        if (existingDepartment != null && existingDepartment.get().getIdDepartement() != id) {
            return new ResponseEntity<>("Le code de département existe déjà pour un autre département", HttpStatus.CONFLICT);
        }
        Department department1 = new Department();
        department1.setIdDepartement(department.getIdDepartement());
        department1.setNomDepartement(department.getNomDepartement());
        department1.setDescriptionDepartement(department.getDescriptionDepartement());
        // Mise à jour du département
        departmentService.update(department1, id);
        return new ResponseEntity<>("Département mis à jour avec succès", HttpStatus.OK);
    }
}
