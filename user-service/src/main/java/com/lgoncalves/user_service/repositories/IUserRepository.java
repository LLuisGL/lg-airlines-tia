package com.lgoncalves.user_service.repositories;

import com.lgoncalves.user_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, String> {

}
