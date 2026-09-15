package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Prova;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {
}
