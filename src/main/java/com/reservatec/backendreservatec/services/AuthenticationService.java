package com.reservatec.backendreservatec.services;

import com.reservatec.backendreservatec.domains.UsuarioTO;
import com.reservatec.backendreservatec.entities.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Optional;

public interface AuthenticationService {

    Optional<UsuarioTO> getUserFromToken(OAuth2AuthenticationToken token);

    UsuarioTO createUserFromToken(OAuth2AuthenticationToken token);

    boolean isAuthenticated(Authentication authentication);

    Usuario convertToEntity(UsuarioTO usuarioTO); // Añadido
}
