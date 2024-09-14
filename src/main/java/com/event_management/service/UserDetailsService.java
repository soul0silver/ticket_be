package com.event_management.service;

import com.event_management.DTO.UserDTO;
import com.event_management.model.Role;
import com.event_management.model.User;
import com.event_management.repository.RoleRepository;
import com.event_management.security.user_principle.UserPrinciple;
import com.event_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    @Transactional
    public UserPrinciple loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email);
        List<String> roles= roleRepository.findRoleByUsersId(user.getId()).stream().map(Role::getName).collect(Collectors.toList());
        UserDTO userDTO=new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getAvatar(),
                user.isSystem(),
                roles
        );
        return UserPrinciple.build(userDTO);
    }
}
