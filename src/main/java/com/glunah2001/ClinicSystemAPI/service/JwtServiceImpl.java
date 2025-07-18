package com.glunah2001.ClinicSystemAPI.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.glunah2001.ClinicSystemAPI.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{

    @Value("${application.spring.security.secret-key}")
    private String secretKey;
    @Value("${application.spring.security.expiration}")
    private long expiration;
    @Value("${application.spring.security.refresh-expiration}")
    private long refreshExpiration;

    @Override
    public String generateAccessToken(final User user) {
        return buildToken(user, expiration);
    }

    @Override
    public String generateRefreshToken(final User user) {
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(final User user, long expiration){
        return Jwts.builder()
                .subject(user.getUsername())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
