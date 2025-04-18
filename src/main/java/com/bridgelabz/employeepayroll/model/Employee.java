package com.bridgelabz.employeepayroll.model;

import com.bridgelabz.employeepayroll.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    private String name;
    private double salary;
    private String gender;
    private LocalDate startDate;
    private String note;
    private  String profilePic;

    @ElementCollection
    @CollectionTable(name = "employee_department",joinColumns = @JoinColumn(name = "id"))
    @Column(name = "department")
    private List<String> department;

    public Employee(){}

    public Employee(EmployeeDTO employeeDTO) {
        this.updateEmployeeData(employeeDTO);
    }

    private void updateEmployeeData(EmployeeDTO employeeDTO) {
        this.name = employeeDTO.name;
        this.salary = employeeDTO.salary;
        this.gender = employeeDTO.gender;
        this.startDate = employeeDTO.startDate;
        this.note = employeeDTO.note;
        this.profilePic = employeeDTO.profilePic;
        this.department = new ArrayList<>(employeeDTO.department);

    }
//
//    public void setEmployeeId(int employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public int getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
}

