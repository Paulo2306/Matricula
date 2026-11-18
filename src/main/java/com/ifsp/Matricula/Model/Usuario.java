
package com.ifsp.Matricula.Model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false, length = 30)
    private UsuarioRole usuarioRole = UsuarioRole.USER;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @OneToOne
    @JoinColumn(name = "pessoa_id", unique = true)
    private Pessoa pessoa;

    public Usuario() {
    }

    public Usuario(String username, String password,
                   UsuarioRole papel, String fotoPerfil, Pessoa pessoa) {
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

    @Override
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
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
        if (usuarioRole == UsuarioRole.ADMIN) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USUARIO")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
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
