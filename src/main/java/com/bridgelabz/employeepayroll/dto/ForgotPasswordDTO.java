package com.bridgelabz.employeepayroll.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ForgotPasswordDTO {

    @Email(message = "Email should be valid")
    private String email;

    @Size( min = 6, max = 6, message = "OTP must be of 6 characters")
    private String otp;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
