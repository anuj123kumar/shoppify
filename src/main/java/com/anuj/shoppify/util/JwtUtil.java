package com.anuj.shoppify.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


@Component
public class JwtUtil {

    private final static long EXPIRATION = 1000 * 60 * 60;
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode("ThisISXTYdehfh123secretgfuegrfiuehroighp3ut0i3o4tp34bfubu4bgriu4oigb");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String emailId){
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
