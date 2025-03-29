package com.lacamentopeca.pedidosDePecas.DTO;

import com.lacamentopeca.pedidosDePecas.model.UserRoles;
import jakarta.validation.constraints.NotNull;

public record RegisterUsuarios(
        @NotNull
        String username,
        @NotNull
        String password,
        @NotNull
        UserRoles role
) {
}
