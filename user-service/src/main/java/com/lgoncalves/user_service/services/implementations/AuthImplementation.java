package com.lgoncalves.user_service.services.implementations;


import com.lgoncalves.user_service.dtos.LoginDTO;
import com.lgoncalves.user_service.dtos.RegisterDTO;
import com.lgoncalves.user_service.dtos.TokenDTO;
import com.lgoncalves.user_service.dtos.UserDTO;
import com.lgoncalves.user_service.entities.TokenEntity;
import com.lgoncalves.user_service.entities.UserEntity;
import com.lgoncalves.user_service.exceptions.auth.InvalidCorreoException;
import com.lgoncalves.user_service.exceptions.auth.InvalidLoginException;
import com.lgoncalves.user_service.repositories.ITokenRepository;
import com.lgoncalves.user_service.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthImplementation {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final JwtImplementation jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenDTO register(RegisterDTO request){
        UserEntity userEntity = new UserEntity();

        if (!isValidEmail(request.correo())) throw new InvalidCorreoException("Introduzca un correo válido.");

        UserDTO usuarioDTO = UserDTO.builder()
                .nombre(request.nombre())
                .correo(request.correo())
                .clave(passwordEncoder.encode(request.clave()))
                .build();
        userEntity.setDTO(usuarioDTO);
        UserEntity usuarioGuardado = userRepository.save(userEntity);
        var token = jwtService.generateToken(userEntity);
        var refreshToken = jwtService.refreshToken(userEntity);
        saveToken(usuarioGuardado, token);
        return new TokenDTO(token,refreshToken);
    }

    private boolean isValidEmail(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return correo != null && correo.matches(regex);
    }

    public TokenDTO login(LoginDTO request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.correo(),
                            request.clave()
                    )
            );
        } catch(AuthenticationException ex){
            //Coloco esta excepcion de aqui para tenerlo mas personalizado a pesar de que ya el authenticate trae consigo una excepcion
            throw new InvalidLoginException("Credenciales invalidas, vuelva a introducirlas.");
        }

        UserEntity userEntity = userRepository.findByCorreo(request.correo())
                .orElseThrow(() -> new InvalidLoginException("Credenciales invalidas, vuelva a introducirlas."));
        String token = jwtService.generateToken(userEntity);
        String refreshToken = jwtService.refreshToken(userEntity);
        revokeAllUserTokens(userEntity);
        saveToken(userEntity, token);
        return new TokenDTO(token, refreshToken);
    }

    private void revokeAllUserTokens(UserEntity userEntity){
        List<TokenEntity> userEntityTokens = tokenRepository.findAllByExpiredFalseOrRevokedFalseAndUserEntity_Id(userEntity.getId());
        if(!userEntityTokens.isEmpty()){
            for(TokenEntity token:userEntityTokens){
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(userEntityTokens);
        }
    }

    private void saveToken(UserEntity userEntity, String token){
        TokenEntity jwtToken = TokenEntity.builder()
                .userEntity(userEntity)
                .token(token)
                .tokenType(TokenEntity.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }

}
