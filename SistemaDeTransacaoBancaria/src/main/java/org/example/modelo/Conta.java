package org.example.modelo;

import java.math.BigDecimal;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Conta {

    private Cliente titular;
    private BigDecimal saldo;
    private Status status;
    private int numeroConta;
    private List<Transacao> historicoTransacoes;

    public Conta(Cliente titular){
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.status = status.ATIVA;
        this.numeroConta = gerarNumeroConta();
        this.historicoTransacoes = new ArrayList<>();
    }

    public void adicionarSaldo(BigDecimal valor){
        this.saldo = saldo.add(valor);
    }

    public void subtrairSaldo(BigDecimal valor){
        this.saldo = saldo.subtract(valor);
    }

    public int gerarNumeroConta(){
        Random random = new Random();
        int numeroConta;

        numeroConta = random.nextInt(1000000);
        return numeroConta;
    }

    public List<Transacao> getHistoricoTransferencias() {
        return historicoTransferencias;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "titular=" + titular +
                ", saldo=" + saldo +
                ", status=" + status +
                ", numeroConta=" + numeroConta +
                '}';
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void desativarConta()
    {
        this.status = status.DESATIVA;
    }

    public void ativarConta()
    {
        this.status = status.ATIVA;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }
}