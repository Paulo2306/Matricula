package com.ifsp.Matricula.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("aluno")
public class Aluno extends Pessoa{

    @Column(name = "prontuario")
    private String prontuario;

    @Column(name = "anoIngresso")
    private int anoIngresso;

    @Column(name = "anoSaida")
    private int anoSaida;

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


}
