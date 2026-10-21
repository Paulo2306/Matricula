package com.ifsp.Matricula.Model;

public enum UsuarioRole {
    ADMIN("admin"),
    USER("usuario");

    private String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}