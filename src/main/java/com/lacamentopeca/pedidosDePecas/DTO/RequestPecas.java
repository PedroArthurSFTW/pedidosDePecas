package com.lacamentopeca.pedidosDePecas.DTO;

import jakarta.validation.constraints.NotNull;

public record RequestPecas(
        Integer id,
        @NotNull
        String nome_peca,
        @NotNull
        Integer codPecaDatasul,
        @NotNull
        String partNumber,
        @NotNull
        String fabricante
)
{}
