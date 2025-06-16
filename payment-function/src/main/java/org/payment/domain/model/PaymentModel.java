package org.payment.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.UUID;

public class PaymentModel {

    private String id = UUID.randomUUID().toString();
    private String paymentId = UUID.randomUUID().toString();;
    private String status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dataAprovacao;
    private String nome;
    private String email;
    private String modelo;
    private int ano;
    private String tempoAluguel;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String data;
    private boolean notificationEnviada = false;

    public PaymentModel() {
    }

    public String getId() {
        return id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(String dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isNotificationEnviada() {
        return notificationEnviada;
    }

    public void setNotificationEnviada(boolean notificationEnviada) {
        this.notificationEnviada = notificationEnviada;
    }

    @Override
    public String toString() {
        return "PaymentModel{" +
                "id='" + id + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", status='" + status + '\'' +
                ", dataAprovacao='" + dataAprovacao + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", tempoAluguel='" + tempoAluguel + '\'' +
                ", data='" + data + '\'' +
                ", notificationEnviada=" + notificationEnviada +
                '}';
    }
}
