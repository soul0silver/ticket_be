package com.userservice.password_reset;

import com.userservice.model.User;

public interface PasswordResetService {
    public void generatePassReset(User user, String token);
    public String validateToken(String token);
    public User findByToken(String token);
}
