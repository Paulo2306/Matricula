package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Disciplina;


@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByCursoId(long id);    
}