package com.lgoncalves.flights_service.controllers;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.services.interfaces.IFlightService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
@AllArgsConstructor
public class FlightController {

    private IFlightService flightService;


}
