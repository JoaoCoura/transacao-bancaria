package org.example.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private Operacao operacao;

    public Transacao(Conta origem, Conta destino, BigDecimal valor, Operacao operacao) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.operacao = operacao;
        this.dataHora = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "origem: " + (origem != null ? origem.getNumeroConta() : "N/A") +
                ", destino: " + (destino != null ? destino.getNumeroConta() : "N/A") +
                ", valor: " + valor +
                ", dataHora: " + dataHora +
                ", Operacao: " + operacao +
                '}';
    }

    public Conta getOrigem(){
        return origem;
    }

    public Conta getDestino(){
        return destino;
    }

}
