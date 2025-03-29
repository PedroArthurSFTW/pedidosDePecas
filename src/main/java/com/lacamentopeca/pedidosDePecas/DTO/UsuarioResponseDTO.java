package com.lacamentopeca.pedidosDePecas.DTO;

import com.lacamentopeca.pedidosDePecas.model.UserRoles;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;

public record UsuarioResponseDTO(
        Integer id,
        String username,
        UserRoles role,
        String status
) {
    public UsuarioResponseDTO(Usuarios usuario) {
        this(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNon_locked_name()
        );
    }
}