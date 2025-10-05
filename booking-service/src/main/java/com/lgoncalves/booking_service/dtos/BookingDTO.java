package com.lgoncalves.booking_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    private String id;
    private String usuario_id;
    private String vuelo_id;
    private String estado;

}
