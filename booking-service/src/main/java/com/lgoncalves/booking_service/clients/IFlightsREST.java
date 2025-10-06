package com.lgoncalves.booking_service.clients;

import com.lgoncalves.booking_service.dtos.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${feign.flights.info.id}", url = "${feign.flights.info.url}")
public interface IFlightsREST {

    @GetMapping("/id/{id}")
    ResponseEntity<FlightDTO> getById(@PathVariable String id);


    @GetMapping("/cambiarDisponibilidad/{vuelo_id}")
    ResponseEntity<FlightDTO> decFlightDisponibility(@PathVariable String vuelo_id, @RequestParam boolean isIncrement);

}
