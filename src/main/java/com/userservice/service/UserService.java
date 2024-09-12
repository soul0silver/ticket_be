package com.userservice.service;

import com.userservice.DTO.FormReg;
import com.userservice.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> findByUsername(String username);
    ResponseEntity<?> save(FormReg formReg);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> getAll(int page,String sort);
    ResponseEntity<?> getAllStudent(int page,String sort);
    ResponseEntity<?> getAllTeacher(int page);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    User findByEmail(String email);
    ResponseEntity<?> activeToLecture(long id);
    ResponseEntity<?> summary();
    ResponseEntity<?> disableAcc(long id);
    ResponseEntity<?> activeAcc(long id);

    ResponseEntity<?> countStudent();
    ResponseEntity<?> countTeacher();
    ResponseEntity<?> findEarnOfTeacher(int page);
    ResponseEntity<?> quantityUserRegisByMonth();
    ResponseEntity<?> revenueByMonth();
}
