package com.reservatec.backendreservatec.services.impl;

import com.reservatec.backendreservatec.domains.UsuarioTO;
import com.reservatec.backendreservatec.entities.Usuario;
import com.reservatec.backendreservatec.mappers.UsuarioMapper;
import com.reservatec.backendreservatec.services.AuthenticationService;
import com.reservatec.backendreservatec.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public AuthenticationServiceImpl(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Optional<UsuarioTO> getUserFromToken(OAuth2AuthenticationToken token) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        String email = (String) attributes.get("email");
        return usuarioService.findByEmail(email)
                .map(usuarioMapper::toTO);
    }

    @Override
    public UsuarioTO createUserFromToken(OAuth2AuthenticationToken token) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        UsuarioTO usuarioTO = new UsuarioTO();
        usuarioTO.setEmail((String) attributes.get("email"));
        usuarioTO.setNombres((String) attributes.get("name"));
        return usuarioTO;
    }

    @Override
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    @Override
    public Usuario convertToEntity(UsuarioTO usuarioTO) {
        return usuarioMapper.toEntity(usuarioTO);
    }
}
