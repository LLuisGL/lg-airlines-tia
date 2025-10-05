package com.lgoncalves.booking_service.repositories;

import com.lgoncalves.booking_service.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<BookingEntity, String> {
}
