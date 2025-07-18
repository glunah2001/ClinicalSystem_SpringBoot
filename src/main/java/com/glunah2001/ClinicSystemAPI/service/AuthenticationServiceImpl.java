package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;
import com.glunah2001.ClinicSystemAPI.enums.TokenType;
import com.glunah2001.ClinicSystemAPI.model.Token;
import com.glunah2001.ClinicSystemAPI.model.User;
import com.glunah2001.ClinicSystemAPI.repository.TokenRepository;
import com.glunah2001.ClinicSystemAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public TokenResponse login(final UserLoginDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.username(),
                        userDto.password()
                )
        );
        var user = userRepository.findByUsername(userDto.username()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(accessToken, user);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public TokenResponse refresh(String authHeader) {
        return null;
    }

    private void saveUserToken(final String jwt, final User user){
        var token = Token.builder()
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserToken(final User user){
        final List<Token> tokens = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());

        if(tokens.isEmpty()) return;

        tokens.forEach(token ->{
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(tokens);
    }
}
