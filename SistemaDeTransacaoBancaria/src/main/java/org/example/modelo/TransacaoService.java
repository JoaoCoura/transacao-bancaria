package org.example.modelo;

import java.math.BigDecimal;

public class TransacaoService {

    public void depositar(Conta destino, BigDecimal valor) {
        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException("Não é possível depositar em uma conta inativa.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        }

        destino.adicionarSaldo(valor);
        System.out.println("Depósito de R$" + valor + " realizado com sucesso. Saldo atual: " + destino.getSaldo());
    }

    public void sacar(Conta origem, BigDecimal valor) {
        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException("Não é possivel sacar de uma conta inativa.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor do saque deve ser positivo.");
        }

        if(origem.getSaldo().compareTo(valor) < 0){
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        origem.subtrairSaldo(valor);
        System.out.println("Saque de R$" + valor + " realizado com sucesso. Saldo atual: " + origem.getSaldo());
    }

    public void transferir(Conta origem, Conta destino, BigDecimal valor) {

        if (destino.getNumeroConta() == origem.getNumeroConta()) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        }

        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException("Não é possível transferir de uma conta inativa.");
        }

        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException("Não é possível transferir para uma conta inativa.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor da transferência deve ser positivo.");
        }

        if(origem.getSaldo().compareTo(valor) < 0){
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        origem.subtrairSaldo(valor);
        destino.adicionarSaldo(valor);
        System.out.println("Transferência de R$" + valor + " de "+ origem.getTitular().getNome() + " para " +
                destino.getTitular().getNome() + " realizada com sucesso!");
    }
}

