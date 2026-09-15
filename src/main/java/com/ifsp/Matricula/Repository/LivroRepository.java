package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
