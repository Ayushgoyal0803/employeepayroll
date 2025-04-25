package com.bridgelabz.employeepayroll.controller;

import com.bridgelabz.employeepayroll.dto.*;
import com.bridgelabz.employeepayroll.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        ResponseDTO response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        ResponseDTO response = authService.login(loginDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgotPassword(@Valid @RequestBody SendOtpDTO sendOtpDTO){
        ResponseDTO responseDTO = authService.forgotPassword(sendOtpDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.valueOf(responseDTO.getStatusCode()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO){
        ResponseDTO responseDTO = authService.resetPassword(forgotPasswordDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.valueOf(responseDTO.getStatusCode()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String authorizationHeader){
        ResponseDTO responseDTO = authService.changePassword(changePasswordDTO,authorizationHeader);
        return new ResponseEntity<>(responseDTO,HttpStatus.valueOf(responseDTO.getStatusCode()));

    }

}

