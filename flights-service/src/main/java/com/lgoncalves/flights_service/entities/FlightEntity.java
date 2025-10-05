package com.lgoncalves.flights_service.entities;

import com.lgoncalves.flights_service.dtos.FlightDTO;
import com.lgoncalves.flights_service.utils.IMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Flights")
@Data
@NoArgsConstructor
public class FlightEntity implements IMapper<FlightDTO> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    @Column(name = "origen", updatable = true, nullable = false)
    private String origen;
    @Column(name = "destino", updatable = true, nullable = false)
    private String destino;
    @Column(name = "fecha", updatable = true, nullable = false)
    private Date fecha;
    @Column(name = "horario", updatable = true, nullable = false)
    private long horario;
    @Column(name = "disponibilidad", updatable = true, nullable = false)
    private int disponibilidad;

    @Override
    public FlightDTO getDTO(){
        return FlightDTO.builder()
                .id(id)
                .origen(origen)
                .destino(destino)
                .fecha(fecha)
                .horario(horario)
                .disponibilidad(disponibilidad)
                .build();
    }

    @Override
    public void setDTO(FlightDTO flightDTO) {
        this.id = flightDTO.getId();
        this.origen = flightDTO.getOrigen();
        this.destino = flightDTO.getDestino();
        this.fecha = flightDTO.getFecha();
        this.horario = flightDTO.getHorario();
        this.disponibilidad = flightDTO.getDisponibilidad();
    }
}
