package com.lgoncalves.booking_service.services.implementations;

import com.lgoncalves.booking_service.clients.IFlightsREST;
import com.lgoncalves.booking_service.clients.IUserREST;
import com.lgoncalves.booking_service.dtos.BookingDTO;
import com.lgoncalves.booking_service.dtos.FlightDTO;
import com.lgoncalves.booking_service.dtos.UserDTO;
import com.lgoncalves.booking_service.entities.BookingEntity;
import com.lgoncalves.booking_service.repositories.IBookingRepository;
import com.lgoncalves.booking_service.services.interfaces.IBookingService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final IFlightsREST flightsREST;
    private final IUserREST userREST;

    @Override
    public List<BookingDTO> getAll() {
        return List.of();
    }

    @Override
    public BookingDTO add(BookingDTO bookingDTO) {

        ResponseEntity<FlightDTO> responseEntity = this.flightsREST.getById(bookingDTO.getVuelo_id());
        FlightDTO flight = responseEntity.getBody();
        ResponseEntity<UserDTO> responseEntity2 = this.userREST.getById(bookingDTO.getUsuario_id());

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity2.getStatusCode().is2xxSuccessful() && flight != null && flight.getDisponibilidad() > 0){
            log.info("Add flight {}", bookingDTO );
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setDTO(bookingDTO);
            this.flightsREST.decFlightDisponibility(bookingDTO.getVuelo_id());
            return this.bookingRepository.save(bookingEntity).getDTO();
        } else{
            log.warn("Error booking flight: vuelo_id not valid. Id {}", bookingDTO.getVuelo_id());
            throw new RuntimeException("Vuelo no encontrado con id_vuelo: " + bookingDTO.getVuelo_id() + ", id_usuario: " + bookingDTO.getUsuario_id());
        }

    }

    @Override
    public BookingDTO update(String id, BookingDTO bookingDTO) {
        return null;
    }

    @Override
    public void delete(String id) {
        log.info("Removing flight id {}", id);
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking no encontrado"));
        bookingEntity.setEstado("Cancelado");
        bookingRepository.save(bookingEntity);
    }

    @Override
    public BookingDTO getById(String id) {
        return null;
    }
}
