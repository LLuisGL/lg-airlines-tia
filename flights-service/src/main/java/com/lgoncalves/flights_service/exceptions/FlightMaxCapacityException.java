package com.lgoncalves.flights_service.exceptions;

public class FlightMaxCapacityException extends RuntimeException {
    public FlightMaxCapacityException(String message) {
        super(message);
    }
}
