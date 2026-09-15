package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Matricula;


@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByAlunoIdPessoa(long id);
    boolean existsByOfertaDisciplinaId(long id);
}