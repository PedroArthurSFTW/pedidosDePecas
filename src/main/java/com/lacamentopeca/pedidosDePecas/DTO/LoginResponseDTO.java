package com.lacamentopeca.pedidosDePecas.DTO;


import com.lacamentopeca.pedidosDePecas.model.UserRoles;

public record LoginResponseDTO(
        String token,
        String username,
        Integer id,
        UserRoles role) {
}
