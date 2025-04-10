package org.example.modelo;

import java.math.BigDecimal;
import java.util.Random;

public class Conta {

    private Cliente titular;
    private BigDecimal saldo;
    private BigDecimal limite;
    private TipoConta tipo;
    private TipoStatus status;
    private int numeroConta;
    private static int numeroContaAtual = 1;

    public Conta(Cliente titular){
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.status = TipoStatus.ATIVA;
        this.numeroConta = gerarNumeroConta();
        tipoConta(TipoConta.COMUM);
        this.limite = limiteConta(tipo, saldo);
    }

    public void adicionarSaldo(BigDecimal valor){
        this.saldo = saldo.add(valor);
        this.limite = limiteConta(tipo, saldo); // atualiza o limite
    }

    public void subtrairSaldo(BigDecimal valor){
        this.saldo = saldo.subtract(valor);
        this.limite = limiteConta(tipo, saldo); // atualiza o limite
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
                ", tipo: " + tipo +
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

    public TipoConta getTipo(){
        return tipo;
    }

    public void setTipo(TipoConta tipo){
        this.tipo = tipo;
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
        this.tipo = tipo;
    }

    public BigDecimal limiteConta(TipoConta tipo, BigDecimal saldo){

        BigDecimal limite_base;
    
        switch (tipo) {
            case COMUM:
                limite_base = saldo.multiply(new BigDecimal("0.5"));
                break;
    
            case SILVER:
                limite_base = saldo.multiply(new BigDecimal("0.8"));
                break;
    
            case GOLD:
                limite_base = saldo.multiply(new BigDecimal("1.1"));
                break;
    
            case DIAMOND:
                limite_base = saldo.multiply(new BigDecimal("2"));
                break;
    
            default:
                limite_base = BigDecimal.ZERO;
                break;
        }
    
        BigDecimal limite_conta = saldo.add(limite_base); // aqui sim pode somar o saldo real (mesmo se for negativo)
    
        // Garante que o limite total nunca fique negativo
        return limite_conta.max(BigDecimal.ZERO);
    }
}