package com.userservice.controller;

import com.userservice.service.implement.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sv1")
public class UserController {
    @Autowired
    UserServiceImp serviceImp;

    @GetMapping("/private/get-all")
    public ResponseEntity<?> getAll(@RequestParam int page,@RequestParam String sort){
        return serviceImp.getAll(page,sort);
    }

    @GetMapping("/private/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return serviceImp.findById(id);
    }

    @GetMapping("/private/user/getallstudent")
    public ResponseEntity<?> getAllStudent(@RequestParam int page,@RequestParam String sort){
        return serviceImp.getAllStudent(page,sort);
    }
    @GetMapping("/private/user/getallteacher")
    public ResponseEntity<?> getAllTeacher(@RequestParam int page){
        return serviceImp.getAllTeacher(page);
    }

    @GetMapping("/private/user/findEarnOfTeacher")
    public ResponseEntity<?> findEarnOfTeacher(@RequestParam int page){
        return serviceImp.findEarnOfTeacher(page);
    }

    @PutMapping("/active")
    public ResponseEntity<?> upgradeToLecture(@RequestParam long id){
        return serviceImp.activeToLecture(id);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> summary(){
        return serviceImp.summary();
    }

    @PutMapping("/private/user/disableacc")
    public ResponseEntity<?> disableAcc(@RequestParam long id){
        return serviceImp.disableAcc(id);
    }
    @PutMapping("/private/user/activeacc")
    public ResponseEntity<?> activeAcc(@RequestParam long id){
        return serviceImp.activeAcc(id);
    }

    @GetMapping("/private/user/countstudent")
    public ResponseEntity<?> countStudent() {
        return serviceImp.countStudent();
    }

    @GetMapping("/private/user/countteacher")
    public ResponseEntity<?> countTeacher() {
        return serviceImp.countTeacher();
    }

    @GetMapping("/private/user/quantityUserRegisByMonth")
    public ResponseEntity<?> quantityUserRegisByMonth() {
        return serviceImp.quantityUserRegisByMonth();
    }

    @GetMapping("/private/user/revenueByMonth")
    public ResponseEntity<?> revenueByMonth() {
        return serviceImp.revenueByMonth();
    }
}
