package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.OfertaDisciplina;


@Repository
public interface OfertaDisciplinaRepository extends JpaRepository<OfertaDisciplina, Long> {
    boolean existsByDisciplinaId(long id);

    boolean existsByProfessorIdPessoa(long id);
}