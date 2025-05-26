package org.example.modelo;

public class DescricaoConta {
    private final Cliente titular;
    private final int numeroConta;

    public DescricaoConta(Cliente titular, int numeroConta) {
        this.titular = titular;
        this.numeroConta = numeroConta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public int getNumeroConta() {
        return numeroConta;
    }
}
