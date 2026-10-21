package com.ifsp.Matricula.Model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Renomeado de 'login' para 'username'
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // Renomeado de 'senhaHash' para 'password'
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "usuarioRole", nullable = false)
    private UsuarioRole usuarioRole = UsuarioRole.USER;

    @Column(name = "fotoPerfil")
    private String fotoPerfil;

    @OneToOne
    @JoinColumn(name = "pessoa_id", unique = true, nullable = true)
    private Pessoa pessoa;

    public Usuario() {
    }

    // Construtor atualizado com os novos nomes
    public Usuario(String username, String password, UsuarioRole papel, String fotoPerfil, Pessoa pessoa) {
        this.username = username;
        this.password = password;
        this.usuarioRole = papel;
        this.fotoPerfil = fotoPerfil;
        this.pessoa = pessoa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioRole getPapel() {
        return usuarioRole;
    }

    public void setPapel(UsuarioRole papel) {
        this.usuarioRole = papel;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.usuarioRole == UsuarioRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USUARIO"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    // Métodos do UserDetails simplificados, pois os campos agora têm exatamente os mesmos nomes
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}