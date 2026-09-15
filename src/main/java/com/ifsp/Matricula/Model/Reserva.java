package com.ifsp.Matricula.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dataReserva")
    private LocalDate dataReserva;

    @Column(name = "posicaoFila")
    private int posicaoFila;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusReserva status;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Reserva() {
    }

    public Reserva(LocalDate dataReserva, int posicaoFila, StatusReserva status, Aluno aluno, Livro livro) {
        this.dataReserva = dataReserva;
        this.posicaoFila = posicaoFila;
        this.status = status;
        this.aluno = aluno;
        this.livro = livro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public int getPosicaoFila() {
        return posicaoFila;
    }

    public void setPosicaoFila(int posicaoFila) {
        this.posicaoFila = posicaoFila;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
