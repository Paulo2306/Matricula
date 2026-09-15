package com.ifsp.Matricula.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Prova")
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dataProva")
    private LocalDate data;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "peso")
    private Double peso;

    @ManyToOne
    @JoinColumn(name = "oferta_disciplina_id")
    private OfertaDisciplina ofertaDisciplina;

    public Prova() {
    }

    public Prova(LocalDate data, String conteudo, Double peso, OfertaDisciplina ofertaDisciplina) {
        this.data = data;
        this.conteudo = conteudo;
        this.peso = peso;
        this.ofertaDisciplina = ofertaDisciplina;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public OfertaDisciplina getOfertaDisciplina() {
        return ofertaDisciplina;
    }

    public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
        this.ofertaDisciplina = ofertaDisciplina;
    }
}
