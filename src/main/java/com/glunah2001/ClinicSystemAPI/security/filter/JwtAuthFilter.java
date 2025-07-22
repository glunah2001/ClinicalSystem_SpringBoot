package com.glunah2001.ClinicSystemAPI.security.filter;

import com.glunah2001.ClinicSystemAPI.config.SecurityProperties;
import com.glunah2001.ClinicSystemAPI.model.User;
import com.glunah2001.ClinicSystemAPI.repository.TokenRepository;
import com.glunah2001.ClinicSystemAPI.repository.UserRepository;
import com.glunah2001.ClinicSystemAPI.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();

        for(String publicPath : securityProperties.getWhitelistPaths()){
            if(matcher.match(publicPath, path)){
                filterChain.doFilter(request, response);
                return;
            }
        }

        final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access: Invalid token format.");
            return;
        }

        final var jjwt = header.substring(7);
        final var username = jwtService.getUsername(jjwt);

        if(username == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access: This token might be invalid or corrupted.");
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request, response);
            return;
        }

        final var token = tokenRepository.findByToken(jjwt).orElse(null);
        if(token == null || token.isExpired() || token.isRevoked()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access: This token is no longer valid.");
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if(user.isEmpty()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access: The user contained in this token has been not found.");
            return;
        }

        final boolean isTokenValid = jwtService.isTokenValid(jjwt, user.get());
        if(!isTokenValid){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access: This token isn't valid for this user.");
            return;
        }


        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
