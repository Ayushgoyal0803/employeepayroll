package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeeDTO;
import com.bridgelabz.employeepayroll.dto.ResponseDTO;

public interface EmployeePayrollService {
    ResponseDTO createEmployee(EmployeeDTO employeeDTO);
    ResponseDTO updateEmployee(EmployeeDTO employeeDTO);
    ResponseDTO getEmployeeById(int employeeId);
    ResponseDTO deleteEmployee(int employeeId);
    ResponseDTO getAllEmployees();
}