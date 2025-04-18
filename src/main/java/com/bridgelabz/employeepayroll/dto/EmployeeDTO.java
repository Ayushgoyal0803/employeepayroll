package com.bridgelabz.employeepayroll.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@ToString
public class EmployeeDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",
            message = "Employee name Invalid")
    public String name;

    @Min(value = 500, message = "Min wage should be more than 500")
    public double salary;

    @Pattern(regexp = "male|female", message = "Gender needs to be male or female")
    public String gender;

    @JsonFormat(pattern = "dd MMM yyyy")
    @NotNull(message = "Start date should not be empty")
    @PastOrPresent(message = "Start date should be past or today's date")
    public LocalDate startDate;

    @NotBlank(message = "Note cannot be empty")
    public String note;

    @NotBlank(message = "Profile pic cannot be empty")
    public String profilePic;

    @NotNull(message = "Department should not be empty")
    public List<String> department;


//    public EmployeeDTO(String name, double salary ){
//        this.salary = salary;
//        this.name = name;
//    }
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
//
//    @Override
//    public String toString(){
//        return "name: " + name + "\nsalary: " + salary;
//    }

}
