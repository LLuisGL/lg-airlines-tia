package com.lgoncalves.booking_service.services.implementations;

import com.lgoncalves.booking_service.clients.IFlightsREST;
import com.lgoncalves.booking_service.clients.IUserREST;
import com.lgoncalves.booking_service.dtos.BookingDTO;
import com.lgoncalves.booking_service.dtos.FlightDTO;
import com.lgoncalves.booking_service.dtos.UserDTO;
import com.lgoncalves.booking_service.entities.BookingEntity;
import com.lgoncalves.booking_service.exceptions.booking.ReserveAlreadyRegisteredException;
import com.lgoncalves.booking_service.exceptions.flight.FlightMaxCapacityException;
import com.lgoncalves.booking_service.exceptions.flight.FlightNotFoundException;
import com.lgoncalves.booking_service.exceptions.user.UserNotFoundException;
import com.lgoncalves.booking_service.repositories.IBookingRepository;
import com.lgoncalves.booking_service.services.interfaces.IBookingService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log4j2
@Service
@AllArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final IFlightsREST flightsREST;
    private final IUserREST userREST;

    @Override
    public List<BookingDTO> getAll() {
        return this.bookingRepository
                .findAll()
                .stream()
                .map(BookingEntity::getDTO)
                .toList();
    }

    @Override
    public BookingDTO add(BookingDTO bookingDTO) {

        ResponseEntity<FlightDTO> responseEntity = this.flightsREST.getById(bookingDTO.getVuelo_id());
        ResponseEntity<UserDTO> responseEntity2 = this.userREST.getById(bookingDTO.getUsuario_id());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            if (responseEntity.getStatusCode().equals(NOT_FOUND)) {
                throw new FlightNotFoundException("Vuelo no encontrado.");
            }
            throw new RuntimeException("Error desconocido al obtener el vuelo: " + responseEntity.getStatusCode());
        }

        if (!responseEntity2.getStatusCode().is2xxSuccessful()) {
            if (responseEntity2.getStatusCode().equals(NOT_FOUND)) {
                throw new UserNotFoundException("Usuario no encontrado");
            }
            throw new RuntimeException("Error desconocido al obtener el usuario: " + responseEntity2.getStatusCode());
        }

        List<BookingEntity> bookingEntities = bookingRepository.findByUsuarioIdAndVueloId(bookingDTO.getUsuario_id(), bookingDTO.getVuelo_id());
        if (!bookingEntities.isEmpty() && !bookingEntities.getFirst().getDTO().getEstado().equals("cancelado")) throw new ReserveAlreadyRegisteredException("Ya se ha registrado anteriormente esta reserva");

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setDTO(bookingDTO);
        try {
            ResponseEntity<FlightDTO> responseEntity3 = this.flightsREST.decFlightDisponibility(bookingDTO.getVuelo_id(), false);
            if (!responseEntity3.getStatusCode().is2xxSuccessful())
                throw new FlightMaxCapacityException("El vuelo ha llegado a su máxima capacidad");
        } catch (FeignException.Conflict ex) {
            throw new FlightMaxCapacityException("El vuelo ha llegado a su máxima capacidad");
        }

        BookingDTO bookingDTO1;

        if(!bookingEntities.isEmpty() && bookingEntities.getFirst().getDTO().getEstado().equals("cancelado")){
            bookingDTO1 = this.update(bookingEntities.getFirst().getDTO().getId(),bookingEntities.getFirst().getDTO());
        } else {
            bookingDTO1 = this.bookingRepository.save(bookingEntity).getDTO();
        }
        return bookingDTO1;

    }

    @Override
    public BookingDTO update(String id, BookingDTO bookingDTO) {
        BookingEntity flightEntity = bookingRepository.findById(id).orElseThrow(() -> new FlightNotFoundException("Reserva no encontrada."));
        flightEntity.setDTO(bookingDTO);
        flightEntity.setEstado("activo");
        return this.bookingRepository.save(flightEntity).getDTO();
    }

    @Override
    public void delete(String id) {
        log.info("Removing flight id {}", id);
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking no encontrado"));
        bookingEntity.setEstado("cancelado");
        ResponseEntity<FlightDTO> responseEntity3 = this.flightsREST.decFlightDisponibility(bookingEntity.getDTO().getVuelo_id(), true);
        bookingRepository.save(bookingEntity);
    }

    @Override
    public BookingDTO getById(String id) {
        return null;
    }

    @Override
    public List<BookingDTO> getReservesById(String id) {
        return bookingRepository
                .findByUsuarioId(id)
                .stream()
                .map(BookingEntity::getDTO)
                .toList();
    }
}
