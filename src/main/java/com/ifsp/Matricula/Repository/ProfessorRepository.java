package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Professor;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
}