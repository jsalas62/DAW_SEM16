package com.reservatec.backendreservatec.services;

import com.reservatec.backendreservatec.entities.Horario;
import java.util.List;

public interface HorarioService {
    List<Horario> findAllHorarios();
}
