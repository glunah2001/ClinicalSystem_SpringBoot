package com.glunah2001.ClinicSystemAPI.security;

import com.glunah2001.ClinicSystemAPI.config.SecurityProperties;
import com.glunah2001.ClinicSystemAPI.model.Token;
import com.glunah2001.ClinicSystemAPI.repository.TokenRepository;
import com.glunah2001.ClinicSystemAPI.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final TokenRepository tokenRepository;
    private final AuthenticationProvider authProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    securityProperties.getWhitelistPaths()
                            .forEach(path -> auth.requestMatchers(path).permitAll());
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                    logout.logoutUrl("/auth/logout/")
                            .addLogoutHandler((request,
                                               response,
                                               authentication) -> {
                                final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                                logout(authHeader);
                            })
                            .logoutSuccessHandler((request,
                                                   response,
                                                   authentication) -> {
                                SecurityContextHolder.clearContext();
                            })
                )
                .build();
    }

    private void logout(final String token){
        if(token == null || !token.startsWith("Bearer "))
            throw new IllegalArgumentException("Invalid Token Bearer");

        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken)
                .orElseThrow(() -> new IllegalArgumentException("Access Token has not been registered previously."));
        foundToken.setRevoked(true);
        foundToken.setExpired(true);
        tokenRepository.save(foundToken);
    }
}
