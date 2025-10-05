package com.lgoncalves.user_service.config;

import com.lgoncalves.user_service.dtos.TokenDTO;
import com.lgoncalves.user_service.entities.TokenEntity;
import com.lgoncalves.user_service.entities.UserEntity;
import com.lgoncalves.user_service.repositories.ITokenRepository;
import com.lgoncalves.user_service.repositories.IUserRepository;
import com.lgoncalves.user_service.services.implementations.JwtImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    final private JwtImplementation jwtImplementation;
    final private UserDetailsService userDetailsService;
    final private ITokenRepository tokenRepository;
    final private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException{

        if(request.getServletPath().contains("/auth")){
            filterChain.doFilter(request, response);
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

         String jwtToken = authHeader.substring(7);
         String userEmail = jwtImplementation.extractCorreo(jwtToken);
        if(userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null){
            return;
        }

        TokenEntity token = tokenRepository.findByToken(jwtToken);
        if(token == null || token.isExpired() || token.isRevoked()){
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        Optional<UserEntity> userEntity = userRepository.findByCorreo(userDetails.getUsername());
        if(userEntity.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        boolean isTokenValid = jwtImplementation.isTokenValid(jwtToken, userEntity.get());
        if(!isTokenValid){
            return;
        }

        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);



    }


}
