package org.example.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.MensagensTransacao.*;

public class TransacaoService {
    private List<Transacao> historicoTransacoes = new ArrayList<>();

    public void depositar(Conta destino, BigDecimal valor) {
        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_DEPOSITAR_CONTA_INATIVA);
        }

        destino.adicionarSaldo(valor);
        historicoTransacoes.add(new Transacao(null, destino, valor, TipoTransacao.DEPOSITO));
        System.out.printf((MENSAGEM_SUCESSO) + "%n", "Depósito", valor.doubleValue(), destino.getSaldo().doubleValue());
    }

    public void sacar(Conta origem, BigDecimal valor) {
        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_SACAR_CONTA_INATIVA);
        }

        origem.subtrairSaldo(valor, TipoTransacao.SAQUE);
        historicoTransacoes.add(new Transacao(origem, null, valor, TipoTransacao.SAQUE));
        System.out.printf((MENSAGEM_SUCESSO) + "%n", "Saque", valor.doubleValue(), origem.getSaldo().doubleValue());
    }

    public void transferir(Conta origem, Conta destino, BigDecimal valor) {

        if (destino.getNumeroConta() == origem.getNumeroConta()) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_PROPRIA_CONTA);
        }

        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_CONTA_INATIVA_ORIGEM);
        }

        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_CONTA_INATIVA_DESTINO);
        }

        origem.subtrairSaldo(valor, TipoTransacao.TRANSFERENCIA);
        destino.adicionarSaldo(valor);
        historicoTransacoes.add(new Transacao(origem, destino, valor, TipoTransacao.TRANSFERENCIA));

        System.out.println("Transferência de R$" + valor + " de "+ origem.getTitular().getNome() + " para " +
                destino.getTitular().getNome() + " realizada com sucesso!");
    }

    public void exibirHistoricoConta(int numeroConta) {
        System.out.println("Transações da conta " + numeroConta + ":" );
        for (Transacao t : historicoTransacoes) {
            if ((t.getOrigem() != null && t.getOrigem().getNumeroConta() == numeroConta) || (t.getDestino() != null && t.getDestino().getNumeroConta() == numeroConta)){
                System.out.println(t);
            }
        }
    }
}

