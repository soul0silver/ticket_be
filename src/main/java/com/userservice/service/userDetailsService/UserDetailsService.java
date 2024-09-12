package com.userservice.service.userDetailsService;

import com.userservice.DTO.UserDTO;
import com.userservice.model.User;
import com.userservice.user_principle.UserPrinciple;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findByUsername(username);
        List<String> roles=repository.findRoles(username);
        UserDTO userDTO=new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getAvatar(),
                roles
        );
        if (!repository.existsByUsername(username))
            throw new UsernameNotFoundException("User name not found");

        return UserPrinciple.build(userDTO);
    }
}
