package com.lacamentopeca.pedidosDePecas.services;

import com.lacamentopeca.pedidosDePecas.DTO.AutenticationDTO;
import com.lacamentopeca.pedidosDePecas.DTO.LoginResponseDTO;
import com.lacamentopeca.pedidosDePecas.exceptions.AuthenticationException;
import com.lacamentopeca.pedidosDePecas.infra.security.TokenService;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponseDTO authenticate(AutenticationDTO authData) {
        String username = authData.username().toLowerCase();
        var authentication = new UsernamePasswordAuthenticationToken(
                username,
                authData.password()
        );

        var auth = authenticationManager.authenticate(authentication);

        if (!auth.isAuthenticated()) {
            throw new AuthenticationException("Falha na autenticação");
        }

        var usuario = (Usuarios) auth.getPrincipal();
        String token = tokenService.generateToken(usuario);

        return new LoginResponseDTO(
                token,
                username,
                usuario.getId(),
                usuario.getRole()
        );
    }
}