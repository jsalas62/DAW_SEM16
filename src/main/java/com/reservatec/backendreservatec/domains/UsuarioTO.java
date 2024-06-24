package com.reservatec.backendreservatec.domains;

import com.reservatec.backendreservatec.entities.Carrera;
import com.reservatec.backendreservatec.entities.Estado;
import com.reservatec.backendreservatec.entities.Rol;
import lombok.Data;

@Data
public class UsuarioTO {
    private Long id;
    private String email;
    private String nombres;
    private String codigoTecsup;
    private Rol rol;
    private Estado estado;
    private Carrera carrera;
}