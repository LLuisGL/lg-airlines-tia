package com.lgoncalves.user_service.repositories;

import com.lgoncalves.user_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByCorreo(String correo);
}
