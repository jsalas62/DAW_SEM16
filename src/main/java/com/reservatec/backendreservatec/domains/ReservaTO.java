package com.reservatec.backendreservatec.domains;

import com.reservatec.backendreservatec.entities.Campo;
import com.reservatec.backendreservatec.entities.Estado;
import com.reservatec.backendreservatec.entities.Horario;
import com.reservatec.backendreservatec.entities.Usuario;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaTO {
    private Long id;
    private Usuario usuario;
    private Campo campo;
    private LocalDate fecha;
    private Horario horario;
    private String comentario;
    private Estado estado;
}
