package com.ifsp.Matricula.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


@Entity
@DiscriminatorValue("aluno")
public class Aluno extends Pessoa{

    @Column(name = "prontuario")
    private String prontuario;

    @Column(name = "anoIngresso")
    private int anoIngresso;

    @Column(name = "anoSaida")
    private int anoSaida;

    @OneToMany(mappedBy = "aluno")
    private List<Emprestimo> emprestimos = new ArrayList<>();

    @OneToMany(mappedBy = "aluno")
    private List<Reserva> reservas = new ArrayList<>();

    public Aluno(String prontuario, int anoIngresso, int anoSaida, String nome, String email, int idade, String endereco, String telefone) {
        super(nome,email,idade,endereco,telefone );
        this.prontuario = prontuario;
        this.anoIngresso = anoIngresso;
        this.anoSaida = anoSaida;
    }


    public Aluno(){
    }



    public String getProntuario() {
        return prontuario;
    }



    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }



    public int getAnoIngresso() {
        return anoIngresso;
    }



    public void setAnoIngresso(int anoIngresso) {
        this.anoIngresso = anoIngresso;
    }



    public int getAnoSaida() {
        return anoSaida;
    }



    public void setAnoSaida(int anoSaida) {
        this.anoSaida = anoSaida;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

}
