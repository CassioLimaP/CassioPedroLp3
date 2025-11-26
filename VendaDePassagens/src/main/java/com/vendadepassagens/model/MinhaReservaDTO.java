package com.vendadepassagens.model;

import java.time.LocalDateTime;

public class MinhaReservaDTO {
    private String codigoVoo;
    private String origem;
    private String destino;
    private LocalDateTime dataPartida;
    private String assento;
    private String status;

    public MinhaReservaDTO(String codigoVoo, String origem, String destino,
                           LocalDateTime dataPartida, String assento, String status) {
        this.codigoVoo = codigoVoo;
        this.origem = origem;
        this.destino = destino;
        this.dataPartida = dataPartida;
        this.assento = assento;
        this.status = status;
    }

    // Getters são OBRIGATÓRIOS para o TableView funcionar
    public String getCodigoVoo() { return codigoVoo; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public LocalDateTime getDataPartida() { return dataPartida; }
    public String getAssento() { return assento; }
    public String getStatus() { return status; }
}
