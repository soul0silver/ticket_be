package com.event_management.service.implement;

import com.event_management.DTO.request.GoogleSignInReq;
import com.event_management.DTO.request.LoginReq;
import com.event_management.enums.RoleName;
import com.event_management.model.User;
import com.event_management.repository.RoleRepository;
import com.event_management.repository.UserRepository;
import com.event_management.security.JwtProvider;
import com.event_management.security.JwtResponse;
import com.event_management.security.user_principle.UserPrinciple;
import com.event_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public JwtResponse login(LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        return new JwtResponse(userPrincipal.getId(), userPrincipal.getUsername(),userPrincipal.getAvatar(), token ,userPrincipal.getAuthorities());
    }

    @Transactional
    @Override
    public JwtResponse loginWithGoogle(GoogleSignInReq reqDto) {
        if (!userRepository.existsByEmail(reqDto.getEmail())) {
            User user = User.builder()
                    .fullName(reqDto.getFullName())
                    .email(reqDto.getEmail())
                    .avatar(reqDto.getAvatar())
                    .roles(roleRepository.findRoleByName(RoleName.GUEST.getName()))
                    .build();
            userRepository.save(user);
        }
        Authentication authentication =  new UsernamePasswordAuthenticationToken(reqDto.getEmail(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        return new JwtResponse(userPrincipal.getId(), userPrincipal.getUsername(),userPrincipal.getAvatar(), token ,userPrincipal.getAuthorities());
    }
}
