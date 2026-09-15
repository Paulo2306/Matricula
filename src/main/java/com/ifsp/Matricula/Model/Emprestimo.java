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
@Table(name = "Emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dataRetirada")
    private LocalDate dataRetirada;

    @Column(name = "dataPrevista")
    private LocalDate dataPrevista;

    @Column(name = "dataDevolucao")
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEmprestimo status;

    @Column(name = "renovacoes")
    private int renovacoes;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "exemplar_id")
    private Exemplar exemplar;

    public Emprestimo() {
    }

    public Emprestimo(LocalDate dataRetirada, LocalDate dataPrevista, LocalDate dataDevolucao,
            StatusEmprestimo status, int renovacoes, Aluno aluno, Exemplar exemplar) {
        this.dataRetirada = dataRetirada;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
        this.renovacoes = renovacoes;
        this.aluno = aluno;
        this.exemplar = exemplar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public int getRenovacoes() {
        return renovacoes;
    }

    public void setRenovacoes(int renovacoes) {
        this.renovacoes = renovacoes;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }
}
