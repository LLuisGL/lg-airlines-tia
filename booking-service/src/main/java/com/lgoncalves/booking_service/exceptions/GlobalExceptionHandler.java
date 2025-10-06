package com.lgoncalves.booking_service.exceptions;

import com.lgoncalves.booking_service.exceptions.booking.ReserveAlreadyRegisteredException;
import com.lgoncalves.booking_service.exceptions.flight.FlightMaxCapacityException;
import com.lgoncalves.booking_service.exceptions.flight.FlightNotFoundException;
import com.lgoncalves.booking_service.exceptions.auth.InvalidCorreoException;
import com.lgoncalves.booking_service.exceptions.auth.InvalidLoginException;
import com.lgoncalves.booking_service.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReserveAlreadyRegisteredException.class)
    public ResponseEntity<?> handleReserveAlreadyRegistered(ReserveAlreadyRegisteredException ex) {
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, "Ya se ha reservado en este vuelo a nombre de esta misma persona.");
    }


    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<?> handleInvalidLogin(InvalidLoginException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Las credenciales puestas no son correctas, intentelo de nuevo.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "No se encontró un usuario con las especificaciones dadas.");
    }

    @ExceptionHandler(InvalidCorreoException.class)
    public ResponseEntity<?> handleInvalidCorreo(InvalidCorreoException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Introduzca un correo válido.");
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<?> handleFlightNotFound(FlightNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "No se encontró un vuelo con las especificaciones dadas.");
    }

    @ExceptionHandler(FlightMaxCapacityException.class)
    public ResponseEntity<?> handleFlightMaxCapacity(FlightMaxCapacityException ex) {
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, "El vuelo ya no cuenta con disponibilidad.");
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