package com.userservice.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter

@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String username;
    private String type="Bearer";
    private String avatar;
    private String token;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(Long id,String username,String avatar, String token, Collection<? extends GrantedAuthority> roles) {
        this.username = username;
        this.token = token;
        this.roles = roles;
        this.id=id;
        this.avatar=avatar;
    }
}
