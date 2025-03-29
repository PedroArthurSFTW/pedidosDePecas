package com.lacamentopeca.pedidosDePecas.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestPedidos (
        Integer id,
        @NotNull
        Integer peca_id,
        Integer usuarios_id_abertura,
        Integer usuarios_id_fechamento,
        LocalDateTime data_pedidos,
        LocalDateTime data_pedidos_fechamento,
        String status,
        Integer ordem_servico
) {
}