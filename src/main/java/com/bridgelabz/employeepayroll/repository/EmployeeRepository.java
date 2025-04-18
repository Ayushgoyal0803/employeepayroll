package com.bridgelabz.employeepayroll.repository;

import com.bridgelabz.employeepayroll.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    // Find single employee by exact name
    Optional<Employee> findByName(String name);

    // OR, if multiple employees can have same name
    List<Employee> findAllByName(String name);
}
