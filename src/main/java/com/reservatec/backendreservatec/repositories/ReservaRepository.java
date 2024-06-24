package com.reservatec.backendreservatec.repositories;

import com.reservatec.backendreservatec.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
    Optional<Reserva> findByCampoIdAndHorarioIdAndFecha(Long campoId, Long horarioId, LocalDate fecha);
    List<Reserva> findByUsuarioId(Long id);
    List<Reserva> findByUsuarioIdAndEstadoNombre(Long usuarioId, String estadoNombre);
    List<Reserva> findByEstadoNombre(String estadoNombre);



}