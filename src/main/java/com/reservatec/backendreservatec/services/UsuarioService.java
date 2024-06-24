package com.reservatec.backendreservatec.services;

import com.reservatec.backendreservatec.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {
    void saveUsuario(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
    void updateUsuario(Usuario usuario);
}
