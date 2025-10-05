package com.lgoncalves.booking_service.controllers;

import com.lgoncalves.booking_service.dtos.BookingDTO;
import com.lgoncalves.booking_service.services.interfaces.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> generateBooking(@RequestBody BookingDTO bookingDTO){
        BookingDTO booking = bookingService.add(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> removeBooking(@PathVariable String id){
        bookingService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "successfully");
        return ResponseEntity.ok(response);
    }

}
