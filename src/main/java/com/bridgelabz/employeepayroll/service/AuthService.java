package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.*;

public interface AuthService {
    ResponseDTO register(RegisterDTO registerDTO);

    ResponseDTO login(LoginDTO loginDTO);

    ResponseDTO forgotPassword(SendOtpDTO sendOtpDTO);

    ResponseDTO resetPassword(ForgotPasswordDTO forgotPasswordDTO);

    ResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, String authorizationHeader);
}
