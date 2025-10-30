package com.vendadepassagens.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Voo {

    // Campos (exatamente como as colunas do seu banco)
    private Integer idVoo; // Usamos Integer (objeto) para permitir 'null'
    private String codigoVoo;
    private String origem;
    private String destino;
    private LocalDateTime dataPartida;
    private LocalDateTime dataChegada;
    private int capacidade; // int (primitivo)
    private BigDecimal precoAssento;

    // --- Construtores ---

    // Um construtor vazio é útil para frameworks
    public Voo() {
    }

    // Um construtor completo para criar novos voos
    public Voo(String codigoVoo, String origem, String destino,
               LocalDateTime dataPartida, LocalDateTime dataChegada,
               int capacidade, BigDecimal precoAssento) {
        this.codigoVoo = codigoVoo;
        this.origem = origem;
        this.destino = destino;
        this.dataPartida = dataPartida;
        this.dataChegada = dataChegada;
        this.capacidade = capacidade;
        this.precoAssento = precoAssento;
    }

    // --- Getters e Setters ---
    // (Necessários para o Spring RowMapper e para o Swing)

    public Integer getIdVoo() {
        return idVoo;
    }

    public void setIdVoo(Integer idVoo) {
        this.idVoo = idVoo;
    }

    public String getCodigoVoo() {
        return codigoVoo;
    }

    public void setCodigoVoo(String codigoVoo) {
        this.codigoVoo = codigoVoo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDateTime dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDateTime getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDateTime dataChegada) {
        this.dataChegada = dataChegada;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public BigDecimal getPrecoAssento() {
        return precoAssento;
    }

    public void setPrecoAssento(BigDecimal precoAssento) {
        this.precoAssento = precoAssento;
    }

    // (Opcional, mas muito útil para debugar)
    @Override
    public String toString() {
        return "Voo [codigo=" + codigoVoo + ", origem=" + origem + ", destino=" + destino + "]";
    }
}