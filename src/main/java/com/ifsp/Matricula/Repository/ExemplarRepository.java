package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Exemplar;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
}
