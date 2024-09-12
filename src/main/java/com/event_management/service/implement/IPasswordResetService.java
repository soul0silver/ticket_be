package com.event_management.service.implement;

import com.event_management.exception.BusinessException;
import com.event_management.model.PasswordResetToken;
import com.event_management.model.User;
import com.event_management.repository.PasswordResetRepo;
import com.event_management.repository.UserRepository;
import com.event_management.service.PasswordResetService;
import com.event_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class IPasswordResetService implements PasswordResetService {
    @Autowired
    PasswordResetRepo passwordResetRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public void generatePassReset(Long id, String passToken) {
        if (userRepository.existsById(id)) {
            PasswordResetToken token = new PasswordResetToken(passToken, id);
            passwordResetRepo.save(token);
        }
    }

    @Override
    public void resetPassWord(String token, String password) {
        PasswordResetToken resetToken = passwordResetRepo.findByToken(token);
        if (resetToken == null)
            throw new BusinessException("Token reset not found");
        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiration().before(calendar.getTime()))
            throw new BusinessException("expire token");
        User user= userRepository.findById(resetToken.getUid()).orElseThrow(
                () -> new BusinessException("User not found")
        );
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
