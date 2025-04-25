package com.bridgelabz.employeepayroll.controller;

import com.bridgelabz.employeepayroll.dto.EmployeeDTO;
import com.bridgelabz.employeepayroll.dto.ResponseDTO;
import com.bridgelabz.employeepayroll.service.EmployeePayrollService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeepayrollservice")
@Slf4j
public class EmployeePayrollController {

    @Autowired
    private EmployeePayrollService employeeService;

    // get all employees in a database
    @GetMapping(value = {"","/","/get"})
    public ResponseDTO getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // get an employee based on id
    @GetMapping("/get/{empId}")
    public ResponseDTO getEmployee(@PathVariable int empId) {
        return employeeService.getEmployeeById(empId);
    }

    // create a new employee
    @PostMapping("/create")
    public ResponseDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    // update employee details based on id
    @PutMapping("/update")
    public ResponseDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(employeeDTO);
    }

    // delete employee based on id
    @DeleteMapping("/delete/{empId}")
    public ResponseDTO deleteEmployee(@PathVariable int empId) {
        return employeeService.deleteEmployee(empId);
    }
}
