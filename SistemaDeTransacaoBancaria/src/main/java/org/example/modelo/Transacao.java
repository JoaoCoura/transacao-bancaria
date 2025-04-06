package org.example.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private TipoTransacao tipo;

    public Transacao(Conta origem, Conta destino, BigDecimal valor, TipoTransacao tipo) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "origem: " + (origem != null ? origem.getNumeroConta() : "N/A") +
                ", destino: " + (destino != null ? destino.getNumeroConta() : "N/A") +
                ", valor: " + valor +
                ", dataHora: " + dataHora +
                ", tipo: " + tipo +
                '}';
    }

    public Conta getOrigem(){
        return origem;
    }

    public Conta getDestino(){
        return destino;
    }

}
