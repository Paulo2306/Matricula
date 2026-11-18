package com.ifsp.Matricula.Model;

public enum UsuarioRole {
    ADMIN("admin"),
    USER("usuario"),
    ALUNO("aluno"),
    PROFESSOR("professor");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}