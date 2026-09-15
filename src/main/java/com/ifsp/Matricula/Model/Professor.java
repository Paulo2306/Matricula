package com.ifsp.Matricula.Model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;




@Entity
@DiscriminatorValue("professor") 
public class Professor extends Pessoa{
    

    @Column(name = "siape")
    private String siape;

    @Column(name = "area")
    private String area;

    @Column(name = "formacao")
    private String formacao;

    public Professor(String siape, String area, String formacao, String nome, String email, int idade, String endereco, String telefone) {
        super(nome,email,idade,endereco,telefone);
        this.siape = siape;
        this.area = area;
        this.formacao = formacao;
    }

    public Professor() {
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }


}
