package org.example.modelo;

import java.math.BigDecimal;

import static org.example.util.MensagensTransacao.*;

public class Conta {

    private Cliente titular;
    private BigDecimal saldo;
    private BigDecimal limite;
    private TipoConta conta;
    private TipoStatus status;
    private int numeroConta;
    private static int numeroContaAtual = 1;

    public Conta(Cliente titular){
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.status = TipoStatus.ATIVA;
        this.numeroConta = gerarNumeroConta();
        tipoConta(TipoConta.COMUM);
        this.limite = limiteConta(conta, saldo);
    }

    public void adicionarSaldo(BigDecimal valor){
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(MENSAGEM_ERRO_VALOR_NEGATIVO);
        }

        if (valor.scale() > 2) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_CASAS_DECIMAIS);
        }

        this.saldo = saldo.add(valor);
        this.limite = limiteConta(conta, saldo);
    }

    public void subtrairSaldo(BigDecimal valor, TipoTransacao transacao){
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(MENSAGEM_ERRO_VALOR_NEGATIVO);
        }

        if (valor.scale() > 2) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_CASAS_DECIMAIS);
        }

        if (transacao == TipoTransacao.TRANSFERENCIA){
            if (this.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }

            if(this.getLimite().compareTo(valor) == -1){
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE, this.getLimite().doubleValue()));
            }
        }

        else{
            if(this.getSaldo().compareTo(valor) <= 0){
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }
        }

        this.saldo = saldo.subtract(valor);
        this.limite = limiteConta(conta, saldo);
    }

    /*public int gerarNumeroConta(){
        Random random = new Random();
        int numeroConta;

        numeroConta = random.nextInt(1000000);
        return numeroConta;
    }*/

    public int gerarNumeroConta() {
        return numeroContaAtual++;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "titular: " + titular +
                ", saldo: " + saldo +
                ", limite:" + limite +
                ", status: " + status +
                ", numeroConta: " + numeroConta +
                ", tipo: " + conta +
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

    public BigDecimal getLimite(){
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public TipoConta getConta(){
        return conta;
    }

    public void setConta(TipoConta conta){
        this.conta = conta;
    }

    public TipoStatus getStatus() {
        return status;
    }

    public void setStatus(TipoStatus status) {
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
        this.status = TipoStatus.INATIVA;
    }

    public void ativarConta()
    {
        this.status = TipoStatus.ATIVA;
    }

    public void tipoConta(TipoConta tipo){
        this.conta = tipo;
    }

    public static BigDecimal limiteConta(TipoConta tipo, BigDecimal saldo){

        BigDecimal limite_base = switch (tipo) {
            case COMUM -> saldo.multiply(new BigDecimal("0.5"));
            case SILVER -> saldo.multiply(new BigDecimal("0.8"));
            case GOLD -> saldo.multiply(new BigDecimal("1.1"));
            case DIAMOND -> saldo.multiply(new BigDecimal("2"));
        };

        BigDecimal limite_conta = saldo.add(limite_base);

        return limite_conta.max(BigDecimal.ZERO);
    }
}