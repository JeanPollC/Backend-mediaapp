package com.mitocode.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//Clase S1
@Component
public class JwtTokenUtil implements Serializable {

    private final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 HORAS

    @Value("${jwt.secret}") //Expression language ${}
    private String secret;

    public String generateToken(UserDetails userDatails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDatails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));//ADMIN,USER,DBA
        claims.put("test", "mc-test");

        return Jwts.builder()
                .claims(claims)
                .subject(userDatails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    //utils

    public Claims getAllClaimsFromToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    public String getUserNameFromToken(String token){
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUserNameFromToken(token);
        return (username.equalsIgnoreCase(userDetails.getUsername())) && !isTokenExpired(token);
    }


}
