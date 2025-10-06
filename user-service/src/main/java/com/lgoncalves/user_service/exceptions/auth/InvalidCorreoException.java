package com.lgoncalves.user_service.exceptions.auth;

public class InvalidCorreoException extends RuntimeException {
    public InvalidCorreoException(String message) {
        super(message);
    }
}
