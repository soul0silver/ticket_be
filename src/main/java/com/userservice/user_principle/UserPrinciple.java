package com.userservice.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userservice.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    //Generate user for security
    public static UserPrinciple build(UserDTO userDTO) {
        List<GrantedAuthority> authorities;
        // set role list
        authorities = Arrays.
                stream(userDTO.getRoles().toArray()).
                map((s) -> new SimpleGrantedAuthority(s.toString())).
                collect(Collectors.toList());
        return new UserPrinciple(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getAvatar(),
                authorities
        );
    }

    public String getAvatar() {
        return avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return username;
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
