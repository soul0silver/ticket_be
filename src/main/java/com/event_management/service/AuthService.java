package com.event_management.service;

import com.event_management.DTO.request.LoginReq;
import com.event_management.security.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginReq loginReq);
}
