package com.lgoncalves.user_service.services.implementations;

import com.lgoncalves.user_service.dtos.UserDTO;
import com.lgoncalves.user_service.entities.UserEntity;
import com.lgoncalves.user_service.exceptions.user.UserNotFoundException;
import com.lgoncalves.user_service.repositories.IUserRepository;
import com.lgoncalves.user_service.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class UserImplementation implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public List<UserDTO> getAll() {
        return List.of();
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO update(String id, UserDTO userDTO) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public UserDTO getById(String id) {
        Optional<UserEntity> userEntity = this.userRepository.findById(id);
        if(userEntity.isEmpty()) throw new UserNotFoundException("Usuario no encontrado.");
        return userEntity.get().getDTO();
    }
}
