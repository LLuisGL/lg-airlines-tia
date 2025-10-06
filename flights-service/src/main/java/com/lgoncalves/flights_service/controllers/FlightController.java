package com.lgoncalves.flights_service.controllers;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.services.interfaces.IFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/all")
    public ResponseEntity<List<FlightDTO>> getAllBookings(){
        return ResponseEntity.ok(flightService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable String id){
        FlightDTO flightDTO = flightService.getById(id);
        return ResponseEntity.ok(flightDTO);
    }

    @GetMapping("/cambiarDisponibilidad/{id}")
    public ResponseEntity<FlightDTO> decFlightDisponibility(@PathVariable String id, @RequestParam boolean isIncrement){
        FlightDTO flightDTO = flightService.cambiarDisponibilidad(id,isIncrement);
        return ResponseEntity.ok(flightDTO);
    }


}
