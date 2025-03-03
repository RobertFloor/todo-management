package net.javaguides.todo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    // Generate token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(currentDate)
            .setExpiration(expireDate)
            .signWith(key())
            .compact();

    }

    public String getUsernameFromToken(String token) {
        String username = Jwts.parser()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

        return username;
    }

    public boolean validateToken(String token) {
        Jwts.parser()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token);
        return true;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(jwtSecret));

    }

}
