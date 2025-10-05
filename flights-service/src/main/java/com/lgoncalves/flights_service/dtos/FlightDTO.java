package com.lgoncalves.flights_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDTO {

    private String id;
    private String origen;
    private String destino;
    private Date fecha;
    private long horario;
    private int disponibilidad;

}
