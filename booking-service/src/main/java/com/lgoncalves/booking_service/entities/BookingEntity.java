package com.lgoncalves.booking_service.entities;

import com.lgoncalves.booking_service.dtos.BookingDTO;
import com.lgoncalves.booking_service.utils.IMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Booking")
@Data
@NoArgsConstructor
public class BookingEntity implements IMapper<BookingDTO> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    @Column(name = "usuarioId", updatable = true, nullable = false)
    private String usuarioId;
    @Column(name = "vuelo_id", updatable = true, nullable = false)
    private String vueloId;
    @Column(name = "estado", updatable = true, nullable = false)
    private String estado = "activo";

    @Override
    public BookingDTO getDTO() {
        return BookingDTO.builder()
                .id(this.id)
                .vuelo_id(this.vueloId)
                .estado(this.estado)
                .usuario_id(this.usuarioId)
                .build();
    }

    @Override
    public void setDTO(BookingDTO bookingDTO) {
        this.id = bookingDTO.getId();
        this.estado = (bookingDTO.getEstado() != null) ? bookingDTO.getEstado() : "activo";
        this.vueloId = bookingDTO.getVuelo_id();
        this.usuarioId = bookingDTO.getUsuario_id();
    }
}
