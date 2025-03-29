package com.lacamentopeca.pedidosDePecas.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LockAccountRequest {
    private Integer id;
    private Integer valida;
}