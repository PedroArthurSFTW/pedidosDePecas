package com.lacamentopeca.pedidosDePecas.model;

public enum UserRoles {
    ADMIN("admin"),
    USER("user"),
    SUPERTECNICO("supertecnico"),
    TECNICO("tecnico");
    private String role;
    UserRoles(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
