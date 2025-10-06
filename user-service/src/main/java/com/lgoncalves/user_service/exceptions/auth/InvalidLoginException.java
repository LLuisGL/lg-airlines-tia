package com.lgoncalves.user_service.exceptions.auth;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
