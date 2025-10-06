package com.lgoncalves.booking_service.repositories;

import com.lgoncalves.booking_service.dtos.BookingDTO;
import com.lgoncalves.booking_service.dtos.FlightDTO;
import com.lgoncalves.booking_service.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<BookingEntity, String> {

    List<BookingEntity> findByUsuarioId(String usuario_id);
    List<BookingEntity> findByUsuarioIdAndVueloId(String usuario_id, String vuelo_id);

}
