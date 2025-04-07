package org.example.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransacaoService {
    private List<Transacao> historicoTransacoes = new ArrayList<>();

    public void depositar(Conta destino, BigDecimal valor) {
        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException("Não é possível transferir para uma conta inativa.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }
        destino.adicionarSaldo(valor);
        System.out.println("Depósito de R$" + valor + " realizado com sucesso. Saldo atual: " + destino.getSaldo());
        Transacao transacao = new Transacao(null, destino, valor, TipoTransacao.DEPOSITO);
        salvarTransacao(transacao);
    }

    public void sacar(Conta origem, BigDecimal valor) {
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do saque deve ser positivo.");
        }
        if(origem.getSaldo().compareTo(valor) < 0){
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        origem.subtrairSaldo(valor);
        System.out.println("Saque de R$" + valor + " realizado com sucesso. Saldo atual: " + origem.getSaldo());
        Transacao transacao = new Transacao(origem, null, valor, TipoTransacao.SAQUE);
        salvarTransacao(transacao);
    }

    public void transferir(Conta origem, Conta destino, BigDecimal valor) {

        if (destino.getNumeroConta() == origem.getNumeroConta()) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        }

        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException("Não é possível transferir para uma conta inativa.");
        }

        origem.subtrairSaldo(valor);
        destino.adicionarSaldo(valor);
        System.out.println("Transferência de R$" + valor + " de "+ origem.getTitular().getNome() + " para " + destino.getTitular().getNome() + " realizada com sucesso!");
        Transacao transacao = new Transacao(origem, destino, valor, TipoTransacao.TRANSFERENCIA);
        salvarTransacao(transacao);
    }

    public void salvarTransacao(Transacao transacao){
        historicoTransacoes.add(transacao);
    }

    public void exibirHistoricoConta(int numeroConta) {
        System.out.println("Transações da conta " + numeroConta + ":" );
        for (Transacao t : historicoTransacoes) {
            if ((t.getOrigem() != null && t.getOrigem().getNumeroConta() == numeroConta) || (t.getDestino() != null && t.getDestino().getNumeroConta() == numeroConta)){
                System.out.println(t);
            }
        }
    }

    public void exibirHistorico() {
        System.out.println("Transações Registradas:");
        for (Transacao t : historicoTransacoes) {
                System.out.println(t);
        }
    }
}

