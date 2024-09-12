package com.event_management.security.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.event_management.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPrinciple implements UserDetails {
    private Long id;
    private String fullName;
    @JsonIgnore
    private String password;
    private String email;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public static UserPrinciple build(UserDTO userDTO) {
        List<GrantedAuthority> authorities = userDTO.getRoles().stream()
                .map((s) -> new SimpleGrantedAuthority(s.toString()))
                .collect(Collectors.toList());
        return new UserPrinciple(
                userDTO.getId(),
                userDTO.getFullName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getAvatar(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
