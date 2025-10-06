package com.lgoncalves.flights_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<?> handleFlightNotFound(FlightNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "No se encontró un vuelo con las especificaciones dadas.");
    }

    @ExceptionHandler(FlightMaxCapacityException.class)
    public ResponseEntity<?> handleFlightMaxCapacity(FlightMaxCapacityException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "El vuelo ya no cuenta con disponibilidad.");
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);

        return ResponseEntity.status(status).body(error);
    }
}