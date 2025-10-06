package com.lgoncalves.user_service.clients;

import com.lgoncalves.user_service.dtos.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${feign.booking.info.id}", url = "${feign.booking.info.url}")
public interface IBookingREST {

    @GetMapping("/{id}")
    ResponseEntity<List<BookingDTO>> getReservesByUserId(@PathVariable String user_id);

}