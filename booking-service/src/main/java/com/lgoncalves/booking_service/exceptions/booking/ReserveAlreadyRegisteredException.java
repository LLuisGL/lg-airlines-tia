package com.lgoncalves.booking_service.exceptions.booking;

public class ReserveAlreadyRegisteredException extends RuntimeException {
    public ReserveAlreadyRegisteredException(String message) {
        super(message);
    }
}
