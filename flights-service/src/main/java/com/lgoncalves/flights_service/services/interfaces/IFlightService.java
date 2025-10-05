package com.lgoncalves.flights_service.services.interfaces;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.entities.FlightEntity;
import com.lgoncalves.flights_service.utils.ICrud;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface IFlightService extends ICrud<FlightDTO> {

    List<FlightDTO> filtrarVuelos(String origen, String destino, LocalDate fecha);
    void decrementarDisponibilidad(String vuelo_id);
}
