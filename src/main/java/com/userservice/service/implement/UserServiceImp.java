package com.userservice.service.implement;

import com.userservice.DTO.FormReg;
import com.userservice.model.User;
import com.userservice.model.UserRole;
import com.userservice.model.response.Summary;
import com.userservice.repository.UserRepository;
import com.userservice.repository.UserRoleRepo;
import com.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    UserRoleRepo repo;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> findByUsername(String username) {
        return ResponseEntity.ok(repository.findByUsername(username));
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(FormReg formReg) {
        User user = new User();
        user.setUsername(formReg.getUsername());
        user.setEmail(formReg.getEmail());
        user.setPassword(encoder.encode(formReg.getPassword()));
        user.setActive(true);
        user.setCreate_at(new Date());
        user.setAchievement("");
        user.setMajor("");
        User u = repository.save(user);

        int c = 0;
        int [] role= formReg.getRole();
            for (int r : role) {
                UserRole ur=new UserRole();
                ur.setRid(r);
                ur.setUid(u.getId());
                repo.save(ur);
                c++;
            }
        if (c == formReg.getRole().length)
            return ResponseEntity.status(200).body("success");
        return ResponseEntity.status(500).body("fail");
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        if (!repository.existsById(id)) return ResponseEntity.status(404).body("Not found");
        return ResponseEntity.ok(repository.findById(id).get());
    }

    @Override
    public ResponseEntity<?> getAll(int page,String sort) {

        Pageable pageable= PageRequest.of(page,5,
                Sort.by(Sort.Direction.ASC,sort));
        return ResponseEntity.ok(repository.findAll());
    }

    @Override
    public ResponseEntity<?> getAllStudent(int page, String sort) {
        Pageable pageable= PageRequest.of(page,5,
                Sort.by(Sort.Direction.ASC,sort));
        return ResponseEntity.ok(repository.findAllStudent(pageable));
    }

    @Override
    public ResponseEntity<?> getAllTeacher(int page) {
        Pageable pageable= PageRequest.of(page, 5);
        Page<User> resultList = repository.findAllTeacher(pageable);
//        List<TeacherResponse> teacherResponses = Mapper.mapListFromMaps(resultList);
        return ResponseEntity.ok(resultList);
    }

    @Override
    public ResponseEntity<?> findEarnOfTeacher(int page) {
        Pageable pageable= PageRequest.of(page, 5);
        return ResponseEntity.ok(repository.findEarnOfTeacher(pageable));
    }

    @Override
    public ResponseEntity<?> quantityUserRegisByMonth() {
        return ResponseEntity.ok(repository.quantityUserRegisByMonth());
    }

    @Override
    public ResponseEntity<?> revenueByMonth() {
        return ResponseEntity.ok(repository.revenueByMonth());
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public ResponseEntity<?> activeToLecture(long id) {
        // Check có user có id
        if (!repository.existsById(id)) return ResponseEntity.status(404).body("User not found");
        // Tạo obj user_role
        UserRole userRole = new UserRole();
        // Gán role 2 và id vào obj
        userRole.setRid(2);
        userRole.setUid(id);
        // Check đã tồn tại obj có role = 2 và id đó chưa
        if (repo.existsUserRoleByRidAndUid(2,id)) return ResponseEntity.status(300).body("This account is already a lecture");
        // Lưu obj
        else userRole= repo.save(userRole);
        // Check lại lần nữa và trả thông báo
        if (userRole.getId()!=0)
            return ResponseEntity.ok("You are a lecture now");
        return ResponseEntity.status(300).body("You are not allow to be a lecture");
    }

    @Override
    public ResponseEntity<?> summary() {
        Summary summary=
                new Summary(repository.countUserById(),
                        repository.countActive(),
                        repository.countOff());
        return ResponseEntity.ok(summary);
    }

    @Override
    public ResponseEntity<?> disableAcc(long id) {
        // Check có user có id
        if (!repository.existsById(id)) return ResponseEntity.status(404).body("User not found");
        // Lấy ra user có id
        User user = repository.findById(id).get();
        // Cho active về false
        user.setActive(false);
        repository.save(user);
        return ResponseEntity.ok("Disable successfully");
    }

    @Override
    public ResponseEntity<?> activeAcc(long id) {
        // Check có user có id
        if (!repository.existsById(id)) return ResponseEntity.status(404).body("User not found");
        // Lấy ra user có id
        User user = repository.findById(id).get();
        // Cho active về true
        user.setActive(true);
        repository.save(user);
        return ResponseEntity.ok("Active successfully");
    }

    @Override
    public ResponseEntity<?> countStudent() {
        Integer numberStudent = repository.countStudent();
        return ResponseEntity.ok(numberStudent);
    }

    @Override
    public ResponseEntity<?> countTeacher() {
        Integer numberTeacher = repository.countTeacher();
        return ResponseEntity.ok(numberTeacher);
    }

    public void resetPassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }


}
