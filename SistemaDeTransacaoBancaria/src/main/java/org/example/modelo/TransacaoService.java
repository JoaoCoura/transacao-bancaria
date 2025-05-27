package org.example.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.MensagensTransacao.*;

public class TransacaoService {

    public void depositar(Conta destino, BigDecimal valor) {
        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_DEPOSITAR_CONTA_INATIVA);
        }

        destino.adicionarSaldo(valor);
        destino.adicionarTransacao(new Transacao(null, destino, valor, TipoTransacao.DEPOSITO));


        destino.incrementarPontos(valor.multiply(new BigDecimal("0.05")).intValue());
        destino.verificarTipoConta();

        System.out.printf((MENSAGEM_SUCESSO) + "%n", "Depósito", valor.doubleValue(), destino.getSaldo().doubleValue());
    }

    public void sacar(Conta origem, BigDecimal valor) {
        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_SACAR_CONTA_INATIVA);
        }

        origem.subtrairSaldo(valor, TipoTransacao.SAQUE, false);
        origem.adicionarTransacao(new Transacao(origem, null, valor, TipoTransacao.SAQUE));

        origem.verificarTipoConta();
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

        boolean isContato = origem.getContatos().contains(destino);

        origem.subtrairSaldo(valor, TipoTransacao.TRANSFERENCIA, isContato);
        destino.adicionarSaldo(valor);

        Transacao transacao = new Transacao(origem, destino, valor, TipoTransacao.TRANSFERENCIA);
        origem.adicionarTransacao(transacao);
        destino.adicionarTransacao(transacao);
        destino.incrementarPontos(valor.multiply(new BigDecimal("0.02")).intValue());

        origem.incrementarPontos(valor.multiply(new BigDecimal("0.1")).intValue());
        origem.verificarTipoConta();

        System.out.println("Transferência de R$" + valor + " de "+ origem.getTitular().getNome() + " para " +
                destino.getTitular().getNome() + " realizada com sucesso!");
    }
}