package com.event_management.security;

import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    public static final Logger log= LoggerFactory.getLogger(JwtProvider.class);
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;
        Boolean systemAcc = Boolean.valueOf(request.getHeader("IsSystemAcc"));
        if (request.getRequestURI().contains("/private/") || request.getRequestURI().contains("/login-google")) {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = getJwt(request);
                try {
                    if (!systemAcc){
                        try {
                            FirebaseToken firebaseToken = jwtProvider.validateFirebaseToken(jwtToken);
                            email = firebaseToken.getEmail();
                        } catch (Exception e) {
                            log.error("Cannot set user authentication: {}", e.getMessage());
                        }
                    }
                    else email = jwtProvider.getUserFromJwt(jwtToken);
                } catch (IllegalArgumentException e) {
                    logger.error("Can't find token JWT");
                } catch (ExpiredJwtException e) {
                    logger.error("Token JWT had expired");
                }
            } else {
                logger.warn("JWT Token is not type Bearer");
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                try {
                    if (jwtProvider.validateToken(jwtToken)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        chain.doFilter(request, response);
    }
    private String getJwt(HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");
        if (authHeader!=null && authHeader.startsWith("Bearer")){
            return authHeader.replace("Bearer","");
        }
        return null;
    }

}
