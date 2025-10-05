package com.lgoncalves.flights_service.services.implementations;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.entities.FlightEntity;
import com.lgoncalves.flights_service.repositories.IFlightRepository;
import com.lgoncalves.flights_service.services.interfaces.IFlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements IFlightService {

    private final IFlightRepository flightRepository;

    @Override
    public List<FlightDTO> getAll() {
        return this.flightRepository
                .findAll()
                .stream()
                .map(FlightEntity::getDTO)
                .toList();
    }

    @Override
    public FlightDTO add(FlightDTO flightDTO) {
        log.info("Add flight {}", flightDTO );
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setDTO(flightDTO);
        return this.flightRepository.save(flightEntity).getDTO();
    }

    @Override
    public FlightDTO update(String id, FlightDTO flightDTO) {
        log.info("Update flight {}", flightDTO );
        FlightEntity flightEntity = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        flightEntity.setDTO(flightDTO);
        return this.flightRepository.save(flightEntity).getDTO();
    }

    @Override
    public void delete(String id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Vuelo no encontrado con id: " + id);
        }

        flightRepository.deleteById(id);
    }

    @Override
    public FlightDTO getById(String id) {
        log.info("Get flight by id {}", id);
        Optional<FlightEntity> flightEntity = this.flightRepository.findById(id);
        if(flightEntity.isEmpty()){
            return FlightDTO.builder().build();
        }
        return flightEntity.get().getDTO();
    }

    @Override
    public List<FlightDTO> filtrarVuelos(String origen, String destino, LocalDate fecha) {
        return flightRepository
                .findByOrigenAndDestinoAndFecha(origen, destino, Date.valueOf(fecha))
                .stream()
                .map(FlightEntity::getDTO)
                .toList();
    }

    @Override
    public void decrementarDisponibilidad(String vuelo_id) {
        FlightEntity flightEntity = flightRepository.findById(vuelo_id).orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        flightEntity.setDisponibilidad(flightEntity.getDisponibilidad() - 1);
        this.flightRepository.save(flightEntity);
    }
}
