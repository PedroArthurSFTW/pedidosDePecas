package com.lacamentopeca.pedidosDePecas.model;

import com.lacamentopeca.pedidosDePecas.DTO.RequestPecas;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "pecas")
@Entity(name="pecas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pecas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pecas_seq")
    @SequenceGenerator(name = "pecas_seq", sequenceName = "pecas_seq", allocationSize = 1)
    private Integer id;

    private String nomePeca;

    private Integer codPecaDatasul;

    private String partNumber;

    private String fabricante;

    private Boolean active;

    public Pecas(RequestPecas requestPecas){
        this.nomePeca = requestPecas.nome_peca();
        this.codPecaDatasul = requestPecas.codPecaDatasul();
        this.partNumber = requestPecas.partNumber();
        this.fabricante = requestPecas.fabricante();
        this.active = true;
    }

}
