package com.event_management.controller;

import com.event_management.DTO.request.LoginReq;
import com.event_management.DTO.request.RegisterReqDto;
import com.event_management.security.user_principle.UserPrinciple;
import com.event_management.service.AuthService;
import com.event_management.service.implement.IPasswordResetService;
import com.event_management.security.JwtProvider;
import com.event_management.security.JwtResponse;
import com.event_management.service.implement.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sv1/auth")
public class AuthController {
    @Autowired
    UserServiceImp serviceImp;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IPasswordResetService resetPassword;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDto signForm) {
        return ResponseEntity.ok(serviceImp.save(signForm));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginForm) {
            return ResponseEntity.ok(authService.login(loginForm));

    }




}
