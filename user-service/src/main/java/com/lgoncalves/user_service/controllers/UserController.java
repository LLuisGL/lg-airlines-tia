package com.lgoncalves.user_service.controllers;

import com.lgoncalves.user_service.dtos.UserDTO;
import com.lgoncalves.user_service.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getFlightById(@PathVariable String id){
        UserDTO userDTO = userService.getById(id);
        if(userDTO.getId() == null || userDTO.getId().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }


}
