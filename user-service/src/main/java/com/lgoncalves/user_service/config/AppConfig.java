package com.lgoncalves.user_service.config;

import com.lgoncalves.user_service.entities.UserEntity;
import com.lgoncalves.user_service.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final IUserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return correo -> {
            final UserEntity userEntity = userRepository.findByCorreo(correo)
                    .orElseThrow(()-> new UsernameNotFoundException("User not Found"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.getCorreo())
                    .password(userEntity.getClave())
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
