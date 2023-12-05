package com.gi.gestioncompetence.dto;

public class DepartmentEmployeeCountDTO {
    private String department;
    private long employeeCount;

    public DepartmentEmployeeCountDTO(String department, long employeeCount) {
        this.department = department;
        this.employeeCount = employeeCount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}
