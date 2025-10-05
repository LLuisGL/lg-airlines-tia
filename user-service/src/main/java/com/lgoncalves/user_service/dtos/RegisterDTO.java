package com.lgoncalves.user_service.dtos;

public record RegisterDTO (
        String correo,
        String clave,
        String nombre
) {
}
