package com.lacamentopeca.pedidosDePecas.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomPedidoResponse {
    private Integer id_pedido;
    private Integer COD_PECA_DATASUL;
    private String nome_peca;
    private String part_number;
    private Integer ordem_servico;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data_pedidos;
    private LocalDateTime data_fechamento;
    private String usuarios_abertura;
    private String usuarios_fechamento;
    private String status;
    private String fabricante;
}

