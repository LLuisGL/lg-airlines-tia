package com.lgoncalves.booking_service.exceptions.flight;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String message) { super(message); }
}