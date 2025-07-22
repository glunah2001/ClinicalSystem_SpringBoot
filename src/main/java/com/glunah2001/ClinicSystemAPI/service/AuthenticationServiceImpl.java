package com.glunah2001.ClinicSystemAPI.service;

import com.glunah2001.ClinicSystemAPI.dto.UserLoginDto;
import com.glunah2001.ClinicSystemAPI.dto.token.TokenResponse;
import com.glunah2001.ClinicSystemAPI.enums.TokenType;
import com.glunah2001.ClinicSystemAPI.exception.InvalidRefreshTokenException;
import com.glunah2001.ClinicSystemAPI.model.Token;
import com.glunah2001.ClinicSystemAPI.model.User;
import com.glunah2001.ClinicSystemAPI.repository.TokenRepository;
import com.glunah2001.ClinicSystemAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return tokenGeneration(user);
    }

    @Override
    public TokenResponse refresh(final String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            throw new IllegalArgumentException("Invalid Token Bearer");

        String token = authHeader.substring(7);
        var username = jwtService.getUsername(token);
        if(username == null)
            throw new IllegalArgumentException("Invalid Refresh token due to no username");

        var user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("This username has not been registered."));

        if(!jwtService.isTokenValid(token, user))
            throw new InvalidRefreshTokenException("This refresh token is no longer valid.");

        return tokenGeneration(user);
    }

    private TokenResponse tokenGeneration(User user){
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(accessToken, user);
        return new TokenResponse(accessToken, refreshToken);
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
