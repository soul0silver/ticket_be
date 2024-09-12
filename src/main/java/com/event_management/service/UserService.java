package com.event_management.service;

import com.event_management.DTO.request.RegisterReqDto;

public interface UserService {
    Long save(RegisterReqDto registerReqDto);
}
