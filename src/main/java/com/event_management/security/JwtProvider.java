package com.event_management.security;

import com.event_management.security.user_principle.UserPrinciple;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    public static final Logger log= LoggerFactory.getLogger(JwtProvider.class);
    private final String JWT_SECRET="secretaaaaaaaaaaaaahhhhhhhhh" +
            "hhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaatttttttttttttttttttttttttttttttttt";
    private final long JWT_EXPIRATION=86400000L;

    //create token from user
    public String generateToken(Authentication authentication){
        Date now = new Date();
        Date expiryDate=new Date(now.getTime()+JWT_EXPIRATION);
        UserPrinciple userPrinciple=(UserPrinciple) authentication.getPrincipal();
        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .claim("id",userPrinciple.getId().toString())
                .setSubject(userPrinciple.getUsername())
                .claim("roles",roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key(),SignatureAlgorithm.HS512)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
    //get user from jwt
    public String getUserFromJwt(String token){
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid format jwt->Message:{}",ex);
        }  catch (ExpiredJwtException ex) {
            log.error("Expired jwt token->Message:{}",ex);
        } catch (UnsupportedJwtException e){
            log.error("Unsupported jwt token->Message:{}",e);
        } catch (IllegalArgumentException e){
            log.error("jwt class is empty->Message:{}",e);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public FirebaseToken validateFirebaseToken(String token) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(token);
    }

}
