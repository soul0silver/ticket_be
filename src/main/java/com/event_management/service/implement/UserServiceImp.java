package com.event_management.service.implement;

import com.event_management.DTO.request.RegisterReqDto;
import com.event_management.enums.RoleName;
import com.event_management.exception.BusinessException;
import com.event_management.model.Role;
import com.event_management.model.User;
import com.event_management.repository.UserRepository;
import com.event_management.repository.RoleRepository;
import com.event_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;


    @Override
    public Long save(RegisterReqDto registerReqDto) {
        if (userRepository.existsByEmail(registerReqDto.getEmail())){
            throw new BusinessException("Email is already exist");
        }

        User user = new User();
        user.setEmail(registerReqDto.getEmail());
        user.setPassword(encoder.encode(registerReqDto.getPassword()));
        user.setFullName(registerReqDto.getFullName());
        user.setAvatar(registerReqDto.getAvatar());
        user.setSystem(true);
        Set<Role> roles = roleRepository.findRoleByName(RoleName.GUEST.getName());
        user.setRoles(roles);
        return userRepository.save(user).getId();
    }
}
