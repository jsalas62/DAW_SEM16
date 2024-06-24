package com.reservatec.backendreservatec.services.impl;

import com.reservatec.backendreservatec.entities.Campo;
import com.reservatec.backendreservatec.entities.Estado;
import com.reservatec.backendreservatec.entities.Horario;
import com.reservatec.backendreservatec.entities.Reserva;
import com.reservatec.backendreservatec.repositories.CampoRepository;
import com.reservatec.backendreservatec.repositories.EstadoRepository;
import com.reservatec.backendreservatec.repositories.HorarioRepository;
import com.reservatec.backendreservatec.repositories.ReservaRepository;
import com.reservatec.backendreservatec.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final EstadoRepository estadoRepository;
    private final CampoRepository campoRepository;
    private final HorarioRepository horarioRepository;

    @Autowired
    public ReservaServiceImpl(
            ReservaRepository reservaRepository,
            EstadoRepository estadoRepository,
            CampoRepository campoRepository,
            HorarioRepository horarioRepository) {
        this.reservaRepository = reservaRepository;
        this.estadoRepository = estadoRepository;
        this.campoRepository = campoRepository;
        this.horarioRepository = horarioRepository;

        // Establecer zona horaria por defecto a America/Lima
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"));
    }

    @Override
    @Transactional
    public void crearReserva(Reserva reserva) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        // Validar que la fecha de reserva es hoy o en el futuro
        if (reserva.getFecha().isBefore(today)) {
            throw new IllegalArgumentException("No se puede reservar en fechas pasadas.");
        }

        // Obtener campo y horario desde el repositorio para asegurar que existen
        Campo campo = campoRepository.findById(reserva.getCampo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Campo no encontrado"));
        Horario horario = horarioRepository.findById(reserva.getHorario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Horario no encontrado"));

        // Validar que la hora de reserva es en el futuro solo si la fecha es hoy
        if (reserva.getFecha().isEqual(today) && horario.getHoraInicio().isBefore(currentTime)) {
            throw new IllegalArgumentException("No se puede reservar en horas pasadas si la reserva es para hoy.");
        }

        // Validar que el usuario no tiene otra reserva en la misma fecha
        Optional<Reserva> reservaExistente = reservaRepository.findByUsuarioIdAndFecha(reserva.getUsuario().getId(), reserva.getFecha());
        if (reservaExistente.isPresent()) {
            throw new IllegalArgumentException("El usuario ya tiene una reserva para esta fecha.");
        }

        // Validar que el campo no está reservado en el mismo horario y fecha
        Optional<Reserva> reservaCampoExistente = reservaRepository.findByCampoIdAndHorarioIdAndFecha(campo.getId(), horario.getId(), reserva.getFecha());
        if (reservaCampoExistente.isPresent()) {
            throw new IllegalArgumentException("El campo ya está reservado en este horario para esta fecha.");
        }

        // Validar que el usuario puede hacer una nueva reserva solo una semana después de la última reserva completada
        List<Reserva> reservasCompletadas = reservaRepository.findByUsuarioIdAndEstadoNombre(reserva.getUsuario().getId(), "Completada");
        if (!reservasCompletadas.isEmpty()) {
            Reserva ultimaReserva = reservasCompletadas.get(0); // Asumiendo que está ordenado por fecha descendente
            LocalDate fechaUltimaReserva = ultimaReserva.getFecha();
            if (reserva.getFecha().isBefore(fechaUltimaReserva.plusWeeks(1))) {
                throw new IllegalArgumentException("El usuario solo puede realizar una nueva reserva una semana después de la última reserva completada.");
            }
        }

        // Validar que el usuario no tiene una reserva activa
        List<Reserva> reservasActivas = reservaRepository.findByUsuarioIdAndEstadoNombre(reserva.getUsuario().getId(), "Activo");
        if (!reservasActivas.isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede realizar una nueva reserva mientras tenga una reserva activa.");
        }

        // Asignar estado por defecto
        Estado estadoActivo = estadoRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Estado activo no encontrado"));
        reserva.setEstado(estadoActivo);

        reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> findReservasByUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public void eliminarReserva(Long id) throws Exception {
        Optional<Reserva> optionalReserva = reservaRepository.findById(id);
        if (optionalReserva.isPresent()) {
            reservaRepository.delete(optionalReserva.get());
        } else {
            throw new Exception("Reserva no encontrada");
        }
    }


    @Scheduled(fixedRate = 10000) // Ejecutar cada 10 segundos (10000 milisegundos)
    public void actualizarReservas() {
        List<Reserva> reservasActivas = reservaRepository.findByEstadoNombre("Activo");
        Estado estadoCompletada = estadoRepository.findById(3L).orElse(null);

        if (estadoCompletada != null) {
            for (Reserva reserva : reservasActivas) {
                LocalDateTime now = LocalDateTime.now();
                if (reserva.getFecha().isBefore(now.toLocalDate()) ||
                        (reserva.getFecha().isEqual(now.toLocalDate()) && reserva.getHorario().getHoraFin().isBefore(now.toLocalTime()))) {
                    reserva.setEstado(estadoCompletada);
                    reservaRepository.save(reserva);
                }
            }
        }
    }
}
