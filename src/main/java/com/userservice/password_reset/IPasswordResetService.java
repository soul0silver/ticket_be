package com.userservice.password_reset;

import com.userservice.model.PasswordResetToken;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class IPasswordResetService implements PasswordResetService {
    @Autowired
    PasswordResetRepo passwordResetRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public void generatePassReset(User user, String passToken) {
        if (user != null) {
            PasswordResetToken token = new PasswordResetToken(passToken, user.getId());
            passwordResetRepo.save(token);
        }
    }

    @Override
    public String validateToken(String token) {
        PasswordResetToken resetToken = passwordResetRepo.findByToken(token);
        if (resetToken == null) return "Invalid code";
        Long uid = resetToken.getUid();
        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiration().getTime() - calendar.getTime().getTime() <= 0)
            return "Expired verification link! Please click below link to receive a new one ";
        return "valid";
    }

    @Override
    public User findByToken(String token) {
        User user = userRepo.findById(passwordResetRepo.findByToken(token).getUid()).get();
        return user;
    }
}
