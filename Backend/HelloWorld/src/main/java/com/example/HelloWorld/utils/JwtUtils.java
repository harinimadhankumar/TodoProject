package com.example.HelloWorld.utils;

import java.nio.charset.StandardCharsets;


import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private final String SECRET = " Harini is a good girl so no need to scold her ";
    private final long EXPIRATION = 1000*60*60*24;
    private final Key secretKey =Keys.hmacShaKeyFor(SECRET .getBytes(StandardCharsets.UTF_8));
    public String generateToken(String email)
    {
        
        return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
        .signWith(secretKey,SignatureAlgorithm.HS256)
        .compact();
    }
    public String  extractEmail(String token)
    {
        return   Jwts.parserBuilder()
            .setSigningKey(secretKey).
            build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    public boolean validatejwtToken(String token)
    {
        try{
            extractEmail(token);
            return true;
        }
        catch(Exception e)
        {
            return false;

        }
    }
}
