package com.userservice.controller;

import com.userservice.DTO.FormLogin;
import com.userservice.DTO.FormReg;
import com.userservice.DTO.MyRespon;
import com.userservice.model.Classroom;
import com.userservice.model.User;
import com.userservice.model.request.PasswordResetRequest;
import com.userservice.repository.ClassRepo;
import com.userservice.user_principle.UserPrinciple;
import com.userservice.password_reset.IPasswordResetService;
import com.userservice.security.JwtProvider;
import com.userservice.security.JwtResponse;
import com.userservice.service.implement.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/sv1/auth")
public class AuthController {
    @Autowired
    UserServiceImp serviceImp;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    IPasswordResetService resetPassword;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    JavaMailSender javaMailSender;


    @PostMapping("/save")
    public ResponseEntity<?> register(@RequestBody FormReg signForm) {
        MyRespon myRespon = new MyRespon();
        if (serviceImp.existsByUsername(signForm.getUsername())) {
            myRespon.setMessage("The user name is already used! Try again!");
            return ResponseEntity.status(400).body(myRespon.getMessage());
        }
        if (serviceImp.existsByEmail(signForm.getEmail())) {
            myRespon.setMessage("The email is already used! Try again!");
            return ResponseEntity.status(400).body(myRespon.getMessage());
        }
        if (serviceImp.existsByPhone(signForm.getPhone())) {
            myRespon.setMessage("The phone number is already used! Try again!");
            return ResponseEntity.status(400).body(myRespon.getMessage());
        }
        serviceImp.save(signForm);
        myRespon.setMessage("create user success");
        return new ResponseEntity<>(myRespon.getMessage(), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody FormLogin loginForm) {
        if (!serviceImp.existsByUsername(loginForm.getUsername()))
        return ResponseEntity.status(400).body("Username not found");
        try
        {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
            return ResponseEntity.ok(
                    new JwtResponse(userPrincipal.getId(), userPrincipal.getUsername(),userPrincipal.getAvatar(), token ,userPrincipal.getAuthorities()));
        }
        catch (Exception e){
            return ResponseEntity.status(400).body("Username or password was wrong");
        }
    }


    @PostMapping(value = "/forget-password")
    public ResponseEntity<String> forgotPass(@RequestBody PasswordResetRequest resetRequest, HttpServletRequest request) {
        String token = "";
        User user=null;
        if (serviceImp.existsByEmail(resetRequest.getEmail())) {
            user = serviceImp.findByEmail(resetRequest.getEmail());
            token += new Random().nextLong(100000, 1000000);

        resetPassword.generatePassReset(user, token);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo("aloalo1981998@gmail.com");
        message.setSubject("Password reset code");
        message.setText("Copy this code and paste into "+token+" password reset code");
        javaMailSender.send(message);
        }
        return ResponseEntity.ok("A verified code was been sent to your email. Please check your email!");
    }
    @PostMapping(value = "/reset-password")
    public ResponseEntity<?> resetPass(@RequestBody PasswordResetRequest request){
        if (!resetPassword.validateToken(request.getToken()).equalsIgnoreCase("valid"))
            return ResponseEntity.ok("Invalid code");
        User user= resetPassword.findByToken(request.getToken());
        if (user!= null)
            serviceImp.resetPassword(user,request.getPassword());
        return ResponseEntity.ok("password reset successfully");

    }
    @GetMapping(value = "/student")
    ResponseEntity<?> getStudent(@RequestParam int cid){
        return ResponseEntity.ok(classRepo.getStudent(cid));
    }
    @GetMapping(value = "/getAll")
    ResponseEntity<?> getClasses(@RequestParam long uid){
        return ResponseEntity.ok(classRepo.getAllClass(uid));
    }
    @PostMapping(value = "/class")
    ResponseEntity<?> saveClassroom(@RequestBody Classroom classroom){
        classroom.setCreate_at(new Date());
        return ResponseEntity.ok(classRepo.save(classroom));
    }

    @PostMapping(value = "/startClass")
    ResponseEntity<?> startClass(@RequestBody String link,@RequestParam long id){
        List<Long> list=classRepo.getStudent(id);
        ExecutorService service = Executors.newFixedThreadPool(3);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo("aloalo1981998@gmail.com");
        message.setSubject("Link meet");
        message.setText(link);
        return ResponseEntity.ok("ok");
    }

}
