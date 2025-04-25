package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeeDTO;
import com.bridgelabz.employeepayroll.dto.ResponseDTO;
import com.bridgelabz.employeepayroll.model.Employee;
import com.bridgelabz.employeepayroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeePayrollServiceImpl implements EmployeePayrollService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // method to create an employee
    @Override
    public ResponseDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        employeeRepository.save(employee);
        return new ResponseDTO("Created Employee payroll data successfully", HttpStatus.CREATED , employee);
    }

    // method to get employee data based on id
    @Override
    public ResponseDTO getEmployeeById(int employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()) {
            return new ResponseDTO("Employee fetched successfully", HttpStatus.OK, optionalEmployee);
        } else {
            return new ResponseDTO("Employee not found", HttpStatus.NOT_FOUND, null);
        }
    }

    // method to update employee data based on id
    @Override
    public ResponseDTO updateEmployee(EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findByName(employeeDTO.name);
        if(optionalEmployee.isPresent()) {
            Employee updatedEmployee = optionalEmployee.get();
            updatedEmployee.updateEmployeeData(employeeDTO);
            employeeRepository.save(updatedEmployee);

            return new ResponseDTO("Employee data updated successfully", HttpStatus.OK, updatedEmployee);
        } else {
            return new ResponseDTO("Employee not found", HttpStatus.NOT_FOUND, null);
        }
    }

    // method to delete employee based on id
    @Override
    public ResponseDTO deleteEmployee(int employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(optionalEmployee.isPresent()) {
            Employee deletedEmployee = optionalEmployee.get();

            employeeRepository.delete(deletedEmployee);

            return new ResponseDTO("Employee deleted successfully", HttpStatus.OK,employeeId);
        } else {
            return new ResponseDTO("Employee not found", HttpStatus.NOT_FOUND, null);
        }
    }

    // method to get all employees
    @Override
    public ResponseDTO getAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();

        return new ResponseDTO("Employees fetched successfully", HttpStatus.OK, allEmployees);
    }
}