package com.event_management.controller;

import com.event_management.DTO.request.GoogleSignInReq;
import com.event_management.DTO.request.LoginReq;
import com.event_management.DTO.request.RegisterReqDto;
import com.event_management.service.AuthService;
import com.event_management.service.implement.IPasswordResetService;
import com.event_management.security.JwtProvider;
import com.event_management.service.implement.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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
    @PostMapping("/login-google")
    public ResponseEntity<?> loginGoogle(@RequestBody GoogleSignInReq googleSignInReq) {
        return ResponseEntity.ok(authService.loginWithGoogle(googleSignInReq));
    }


}
