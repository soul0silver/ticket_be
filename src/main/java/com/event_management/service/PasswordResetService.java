package com.event_management.service;

import com.event_management.model.User;

public interface PasswordResetService {
    void generatePassReset(Long id, String token);
    void resetPassWord(String token, String password);
}
