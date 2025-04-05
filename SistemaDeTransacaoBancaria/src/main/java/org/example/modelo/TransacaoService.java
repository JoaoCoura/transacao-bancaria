package org.example.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransacaoService {
    private List<Transacao> historicoTransacoes = new ArrayList<>();

    public void depositar(Conta destino, BigDecimal valor) {
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }
        destino.adicionarSaldo(valor);
        System.out.println("Depósito de R$" + valor + " realizado com sucesso. Saldo atual =" + destino.getSaldo());
        Transacao transacao = new Transacao(null, destino, valor, TipoTransacao.DEPOSITO);
        salvarTransacao(transacao);
        destino.adicionarTransacao(transacao);
    }

    public void sacar(Conta origem, BigDecimal valor) {
        //verificar limite da conta e se eh positivo
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do saque deve ser positivo.");
        }
        if(origem.getSaldo().compareTo(valor) < 0){
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        origem.subtrairSaldo(valor);
        System.out.println("Saque de R$" + valor + " realizado com sucesso. Saldo atual =" + origem.getSaldo());
        Transacao transacao = new Transacao(origem, null, valor, TipoTransacao.SAQUE);
        salvarTransacao(transacao);
        origem.adicionarTransacao(transacao);
    }

    public void transferir(Conta origem, Conta destino, BigDecimal valor) {

        if (destino.getNumeroConta() == origem.getNumeroConta()) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        }

        if(destino.getStatus() == Status.INATIVA){
            throw new IllegalArgumentException("Não é possível transferir para uma conta inativa.");
        }

        origem.subtrairSaldo(valor);
        destino.adicionarSaldo(valor);
        System.out.println("Transferência de R$" + valor + " de "+ origem.getTitular().getNome() + " para " + destino.getTitular().getNome() +" realizada com sucesso!");
        Transacao transacao = new Transacao(origem, destino, valor, TipoTransacao.TRANSFERENCIA);
        salvarTransacao(transacao);
        destino.adicionarTransacao(transacao);
        origem.adicionarTransacao(transacao);
    }

    public void salvarTransacao(Transacao transacao){
        historicoTransacoes.add(transacao);
    }

    public void exibirHistorico() {
        for (Transacao t : historicoTransacoes) {
            System.out.println(t);
        }
    }
}
