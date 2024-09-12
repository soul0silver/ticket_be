package com.event_management.security;

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
    private String email;
    private String type="Bearer";
    private String avatar;
    private String token;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(Long id,String email,String avatar, String token, Collection<? extends GrantedAuthority> roles) {
        this.email = email;
        this.token = token;
        this.roles = roles;
        this.id=id;
        this.avatar=avatar;
    }
}
