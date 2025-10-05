package com.lgoncalves.user_service.services.implementations;

import com.lgoncalves.user_service.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtImplementation {

    @Value("${application.security.jwt.secret-key}")
    private String key;
    @Value("${application.security.jwt.expiration}")
    private long jwtexpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(UserEntity userEntity){
        return buildToken(userEntity, jwtexpiration);
    }

    public String refreshToken(UserEntity userEntity){
        return buildToken(userEntity, refreshExpiration);
    }

    public String buildToken(UserEntity userEntity, long expiration){
        return Jwts.builder()
                .id(userEntity.getId())
                .claims(Map.of("name", userEntity.getNombre()))
                .subject(userEntity.getCorreo())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserEntity userEntity){
        String correo = extractCorreo(token);
        return (correo.equals(userEntity.getCorreo())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(final String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }


    public String extractCorreo(final String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
