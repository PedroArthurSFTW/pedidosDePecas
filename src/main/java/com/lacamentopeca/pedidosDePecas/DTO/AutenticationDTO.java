package com.lacamentopeca.pedidosDePecas.DTO;

public record AutenticationDTO(String username, String password) {
    public String getUsername() {
        return username;
    }

    public Object getPassword() {
        return password;
    }
}
