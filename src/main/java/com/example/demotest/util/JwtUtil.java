package com.example.demotest.util;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "COMS656finalprojectUserLoginServiceSecretKeyhopethisisenoughfor256bits"; // Replace with a secure key
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public String generateToken(String name, String password, String type) {
        return Jwts.builder()
                .setSubject(name)
                .claim("type", type)
                .claim("password", password)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

}
