package com.ifsp.Matricula.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "OfertaDisciplina")
public class OfertaDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne // Trocado @Column por relacionamento
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne // Trocado @Column por relacionamento
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @OneToMany(mappedBy = "ofertaDisciplina")
    private List<Prova> provas = new ArrayList<>();

    public OfertaDisciplina() {
    }

    public OfertaDisciplina(Professor professor, Disciplina disciplina) {
        this.professor = professor;
        this.disciplina = disciplina;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public List<Prova> getProvas() { return provas; }
    public void setProvas(List<Prova> provas) { this.provas = provas; }
}