package com.lgoncalves.user_service.entities;

import com.lgoncalves.user_service.dtos.UserDTO;
import com.lgoncalves.user_service.utils.IMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
public class UserEntity implements IMapper<UserDTO> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    @Column(name = "nombre", updatable = true, nullable = false)
    private String nombre;
    @Column(name = "correo", updatable = true, nullable = false)
    private String correo;
    @Column(name = "clave", updatable = true, nullable = false)
    private String clave;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<TokenEntity> tokens;

    @Override
    public UserDTO getDTO() {
        return UserDTO.builder()
                .id(id)
                .clave(clave)
                .correo(correo)
                .nombre(nombre)
                .build();
    }

    @Override
    public void setDTO(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.nombre = userDTO.getNombre();
        this.correo = userDTO.getCorreo();
        this.clave = userDTO.getClave();
    }
}
