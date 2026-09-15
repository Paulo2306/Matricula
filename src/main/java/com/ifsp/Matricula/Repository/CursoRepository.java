package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Curso;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
}