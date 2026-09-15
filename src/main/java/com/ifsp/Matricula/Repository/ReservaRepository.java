package com.ifsp.Matricula.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.Matricula.Model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
