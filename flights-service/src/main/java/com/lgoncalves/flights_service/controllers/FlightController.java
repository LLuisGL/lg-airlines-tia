package com.lgoncalves.flights_service.controllers;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.services.interfaces.IFlightService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
@RequiredArgsConstructor
public class FlightController {

    private final IFlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightDTO>> buscarVuelos(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<FlightDTO> vuelos = flightService.filtrarVuelos(origen, destino, fecha);
        return ResponseEntity.ok(vuelos);
    }


}
