package com.bridgelabz.employeepayroll.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendOtpDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
