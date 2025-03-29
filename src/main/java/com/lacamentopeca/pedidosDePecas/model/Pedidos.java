package com.lacamentopeca.pedidosDePecas.model;

import com.lacamentopeca.pedidosDePecas.DTO.RequestPedidos;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "pedidos")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedidos_seq")
    @SequenceGenerator(name = "pedidos_seq", sequenceName = "pedidos_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "peca_id")
    private Pecas peca;

    @ManyToOne
    @JoinColumn(name = "usuarios_id_abertura")
    private Usuarios usuarioAbertura;

    @ManyToOne
    @JoinColumn(name = "usuarios_id_fechamento")
    private Usuarios usuarioFechamento;

    private LocalDateTime data_pedidos;

    private LocalDateTime data_pedidos_fechamento;

    private String status;

    private Integer ordem_servico;

    public Pedidos(RequestPedidos requestPedidos){
        this.ordem_servico = requestPedidos.ordem_servico();
        this.status = "SOLICITADO";
    }
}