package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.*;
import com.bridgelabz.employeepayroll.model.User;
import com.bridgelabz.employeepayroll.repository.UserRepository;
import com.bridgelabz.employeepayroll.utility.JwtRequestFilter;
import com.bridgelabz.employeepayroll.utility.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final Map<String, OtpDTO> otpStore = new HashMap<>();
    @Override
    public ResponseDTO register(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            return new ResponseDTO("User already exists", HttpStatus.CONFLICT, null);
        }

        User user = new User();
        user.setUserName(registerDTO.getUserName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);

        return new ResponseDTO("User registered successfully", HttpStatus.CREATED, user.getEmail());
    }

    @Override
    public ResponseDTO login(LoginDTO loginDTO) {
        log.info("Login attempt for user {}",loginDTO.getEmail());
        Optional<User> userExist = userRepository.findByEmail(loginDTO.getEmail());

        if(userExist.isPresent()){
            User user = userExist.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                String token = jwtUtility.generateToken(user.getEmail());

                emailService.sendEmail(user.getEmail(),"Logged in employee payroll app","Hi " + user.getUsername()
                + "\nYou have been successfully logged in!\n" +"Token: " + token);
                user.setToken(token);
                userRepository.save(user);
                log.debug("Login Successful for user: {} - Token generated",user.getEmail());

                return new ResponseDTO("Login successful", HttpStatus.OK, token);
            }
        }

        return new ResponseDTO("Invalid email or password", HttpStatus.UNAUTHORIZED, null);
    }

    @Override
    public ResponseDTO forgotPassword(SendOtpDTO sendOtpDTO) {
        Optional<User> userExist = userRepository.findByEmail(sendOtpDTO.getEmail());

        if(userExist.isPresent()){
            User user = userExist.get();

            String otp = String.format("%06d", new Random().nextInt(999999));

            long expTime = System.currentTimeMillis() + 5 * 60 * 1000;

            otpStore.put(user.getEmail(), new OtpDTO(otp,expTime));

            emailService.sendEmail(user.getEmail(), "Reset Password OTP", "Your OTP is: " + otp + "\nOTP is valid for 5 minutes only.");
            log.info("OTP sent to {}", user.getEmail());
            return new ResponseDTO("Otp sent successfully",HttpStatus.OK,null);
        }

        return new ResponseDTO("No user found",HttpStatus.NOT_FOUND,null);
    }

    @Override
    public ResponseDTO resetPassword(ForgotPasswordDTO forgotPasswordDTO) {
        Optional<User> userExist = userRepository.findByEmail(forgotPasswordDTO.getEmail());

        if(userExist.isPresent()){
            User user = userExist.get();
            OtpDTO storedOtp = otpStore.get(user.getEmail());

            if(storedOtp == null){
                return new ResponseDTO("OTP not requested or expired for this user",HttpStatus.NOT_FOUND,null);
            }

            if(System.currentTimeMillis() > storedOtp.getExpTime() ){
                otpStore.remove(user.getEmail());
                return new ResponseDTO("OTP has expired",HttpStatus.GONE,null);
            }

            if(!storedOtp.getOtp().equals(forgotPasswordDTO.getOtp())){
                return new ResponseDTO("Wrong OTP",HttpStatus.BAD_REQUEST,null);
            }

            user.setPassword(passwordEncoder.encode(forgotPasswordDTO.getPassword()));
            userRepository.save(user);

            // Remove used OTP
            otpStore.remove(user.getEmail());

            return new ResponseDTO("Password has been reset successfully", HttpStatus.OK, null);
        }

        return new ResponseDTO("User not found", HttpStatus.NOT_FOUND, null);
    }

    @Override
    public ResponseDTO changePassword(ChangePasswordDTO changePasswordDTO, String authorizationHeader) {
        log.info("Starting change password process");

        String jwt = "";
        String email = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtility.extractEmail(jwt);
            log.debug("Extracted email from JWT: {}", email);
        } else {
            log.warn("Authorization header is missing or does not start with Bearer");
            return new ResponseDTO("Authorization header is invalid or missing", HttpStatus.UNAUTHORIZED, null);
        }

        if (email == null || email.isEmpty()) {
            log.warn("Email extraction from JWT failed");
            return new ResponseDTO("Invalid token or email not found in token", HttpStatus.UNAUTHORIZED, null);
        }

        Optional<User> userExist = userRepository.findByEmail(email);

        if (userExist.isPresent()) {
            User user = userExist.get();
            log.debug("User found with email: {}", email);

            if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
                log.warn("Old password mismatch for user: {}", email);
                return new ResponseDTO("Old password doesn't match", HttpStatus.BAD_REQUEST, null);
            }

            if (passwordEncoder.matches(changePasswordDTO.getNewPassword(), user.getPassword())) {
                log.warn("New password is the same as the old password for user: {}", email);
                return new ResponseDTO("New password cannot be same as old password", HttpStatus.BAD_REQUEST, null);
            }

            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
            log.info("Password updated successfully for user: {}", email);

            return new ResponseDTO("Password has been updated for user " + email + " successfully", HttpStatus.OK, null);
        }

        log.error("User not found for email: {}", email);
        return new ResponseDTO("User not found", HttpStatus.NOT_FOUND, null);
    }

}
