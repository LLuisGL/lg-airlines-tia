package com.lgoncalves.booking_service.services.interfaces;

import com.lgoncalves.booking_service.dtos.BookingDTO;
import com.lgoncalves.booking_service.dtos.FlightDTO;
import com.lgoncalves.booking_service.utils.ICrud;

import java.util.List;

public interface IBookingService extends ICrud<BookingDTO>{

    List<BookingDTO> getReservesById(String id);

}
