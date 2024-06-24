package com.reservatec.backendreservatec.services;

import com.reservatec.backendreservatec.entities.Reserva;
import java.util.List;

public interface ReservaService {
    void crearReserva(Reserva reserva) throws Exception;
    List<Reserva> findReservasByUsuario(Long usuarioId);

    void eliminarReserva(Long id) throws Exception;

}
