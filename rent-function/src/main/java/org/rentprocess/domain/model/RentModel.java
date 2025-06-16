package org.rentprocess.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RentModel {

    private String nome;
    private String email;
    private String modelo;
    private int ano;
    private String tempoAluguel;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate data;

    public RentModel() {
    }

    public RentModel(String nome, String email, String modelo, int ano, String tempoAluguel, LocalDate data) {
        this.nome = nome;
        this.email = email;
        this.modelo = modelo;
        this.ano = ano;
        this.tempoAluguel = tempoAluguel;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTempoAluguel() {
        return tempoAluguel;
    }

    public void setTempoAluguel(String tempoAluguel) {
        this.tempoAluguel = tempoAluguel;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
