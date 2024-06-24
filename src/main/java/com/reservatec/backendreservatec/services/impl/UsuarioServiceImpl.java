package com.reservatec.backendreservatec.services.impl;

import com.reservatec.backendreservatec.entities.Carrera;
import com.reservatec.backendreservatec.entities.Usuario;
import com.reservatec.backendreservatec.repositories.CarreraRepository;
import com.reservatec.backendreservatec.repositories.EstadoRepository;
import com.reservatec.backendreservatec.repositories.RolRepository;
import com.reservatec.backendreservatec.repositories.UsuarioRepository;
import com.reservatec.backendreservatec.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EstadoRepository estadoRepository;
    private final RolRepository rolRepository;
    private final CarreraRepository carreraRepository;

    // Constructor con inyección de dependencias
    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            EstadoRepository estadoRepository,
            RolRepository rolRepository,
            CarreraRepository carreraRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estadoRepository = estadoRepository;
        this.rolRepository = rolRepository;
        this.carreraRepository = carreraRepository;
    }

    @Override
    @Transactional
    public void saveUsuario(Usuario usuario) {
        Optional<Usuario> existingUserOpt = usuarioRepository.findByEmail(usuario.getEmail());

        if (existingUserOpt.isEmpty()) {
            validateAndSetDefaults(usuario);
            usuarioRepository.save(usuario);
        } else {
            Usuario existingUser = existingUserOpt.get();
            updateExistingUser(existingUser, usuario);
        }
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updateUsuario(Usuario usuario) {
        Usuario existingUser = usuarioRepository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con email: " + usuario.getEmail()));

        updateExistingUser(existingUser, usuario);
    }

    private void validateAndSetDefaults(Usuario usuario) {
        if (usuario.getCodigoTecsup() == null) {
            throw new IllegalArgumentException("El código Tecsup no puede ser nulo.");
        }

        if (usuario.getEstado() == null) {
            usuario.setEstado(estadoRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado")));
        }

        if (usuario.getRol() == null) {
            usuario.setRol(rolRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
        }

        if (usuario.getCarrera() != null && usuario.getCarrera().getId() != null) {
            Carrera carrera = carreraRepository.findById(usuario.getCarrera().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Carrera no encontrada con ID: " + usuario.getCarrera().getId()));
            usuario.setCarrera(carrera);
        }
    }

    private void updateExistingUser(Usuario existingUser, Usuario newUser) {
        if (newUser.getNombres() != null) {
            existingUser.setNombres(newUser.getNombres());
        }

        if (newUser.getCodigoTecsup() != null) {
            existingUser.setCodigoTecsup(newUser.getCodigoTecsup());
        }

        if (newUser.getEstado() != null) {
            existingUser.setEstado(newUser.getEstado());
        }

        if (newUser.getRol() != null) {
            existingUser.setRol(newUser.getRol());
        }

        if (newUser.getCarrera() != null && newUser.getCarrera().getId() != null) {
            Carrera carrera = carreraRepository.findById(newUser.getCarrera().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Carrera no encontrada con ID: " + newUser.getCarrera().getId()));
            existingUser.setCarrera(carrera);
        }

        usuarioRepository.save(existingUser);
    }
}
