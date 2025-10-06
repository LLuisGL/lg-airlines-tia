package com.lgoncalves.booking_service.exceptions.flight;

public class FlightMaxCapacityException extends RuntimeException {
    public FlightMaxCapacityException(String message) {
        super(message);
    }
}
