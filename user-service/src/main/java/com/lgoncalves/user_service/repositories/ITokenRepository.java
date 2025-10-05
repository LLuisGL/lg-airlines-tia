package com.lgoncalves.user_service.repositories;

import com.lgoncalves.user_service.dtos.TokenDTO;
import com.lgoncalves.user_service.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITokenRepository extends JpaRepository<TokenEntity, String> {
    List<TokenEntity> findAllByExpiredFalseOrRevokedFalseAndUserEntity_Id(String id);
    TokenEntity findByToken(String token);
}
