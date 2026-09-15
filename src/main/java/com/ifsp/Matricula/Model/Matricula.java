package com.ifsp.Matricula.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "ofertaDisciplina_id")
    private OfertaDisciplina ofertaDisciplina;   



    public Matricula() {
    }

    public Matricula(Aluno aluno, OfertaDisciplina ofertaDisciplina) {
        this.aluno = aluno;
        this.ofertaDisciplina = ofertaDisciplina;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public OfertaDisciplina getOfertaDisciplina() { return ofertaDisciplina; }
    public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) { this.ofertaDisciplina = ofertaDisciplina; }

}