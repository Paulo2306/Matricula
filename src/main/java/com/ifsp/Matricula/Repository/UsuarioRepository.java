package com.ifsp.Matricula.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ifsp.Matricula.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByPessoa_IdPessoa(Integer idPessoa);
    
    // Retorna Optional para facilitar as verificações no Controller
    Optional<Usuario> findByUsername(String username);
}