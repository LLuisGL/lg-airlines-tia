package com.lgoncalves.user_service.controllers;

import com.lgoncalves.user_service.dtos.BookingDTO;
import com.lgoncalves.user_service.dtos.UserDTO;
import com.lgoncalves.user_service.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<BookingDTO>> getReservesByUserId(@PathVariable String id){
        return ResponseEntity.ok(userService.getReservesByUserId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllBookings(){
        return ResponseEntity.ok(userService.getAll());
    }


}
