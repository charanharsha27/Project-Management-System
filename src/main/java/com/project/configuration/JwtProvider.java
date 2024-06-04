package com.project.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {


    public static final String SECRET_KEY = "cjanrygfakdirh vvsmtocfswbvcjh moqjdxhgcgtrbdjm";
    static SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(Authentication authentication) {

        return Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .signWith(secretKey)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return String.valueOf(claims.get("email"));
    }
}
