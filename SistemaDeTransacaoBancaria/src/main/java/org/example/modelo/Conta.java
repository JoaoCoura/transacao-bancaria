package org.example.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.util.MensagensTransacao.*;

public class Conta {

    private Cliente titular;
    private BigDecimal saldo;
    private BigDecimal limite;
    private BigDecimal limiteContato;
    private TipoConta conta;
    private TipoStatus status;
    private int numeroConta;
    private static int numeroContaAtual = 1;

    private Set<Conta> contatos;

    public Conta(Cliente titular){
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.status = TipoStatus.ATIVA;
        this.numeroConta = gerarNumeroConta();
        tipoConta(TipoConta.COMUM);
        this.limite = limiteConta(conta, saldo);
        this.limiteContato = limiteContaContato(conta, saldo);
        this.contatos = new HashSet<>();
    }

    public void adicionarContato(Conta conta) {
        boolean adicionado = contatos.add(conta);
        if (adicionado) {
            System.out.println("Contato adicionado com sucesso.");
        } else {
            System.out.println("Contato já existe.");
        }
    }

    public void removerContato(Conta conta) {
        boolean removido = contatos.remove(conta);
        if (removido) {
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    public void mostrarContatos()
    {
        for (Conta conta : contatos )
        {
            System.out.println(conta);
        }
    }

    private void validarValor(BigDecimal valor) {
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(MENSAGEM_ERRO_VALOR_NEGATIVO);
        }

        if (valor.scale() > 2) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_CASAS_DECIMAIS);
        }
    }

    public void adicionarSaldo(BigDecimal valor){
        validarValor(valor);
        this.saldo = saldo.add(valor);
        this.limite = limiteConta(conta, saldo);
        this.limiteContato = limiteContaContato(conta, saldo);
    }

    public void subtrairSaldo(BigDecimal valor, TipoTransacao transacao) {
        subtrairSaldo(valor, transacao, false);
    }

    public void subtrairSaldo(BigDecimal valor, TipoTransacao transacao, boolean isContato){
        validarValor(valor);

        if (transacao == TipoTransacao.TRANSFERENCIA){
            if (this.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }
            if(isContato){
                if(this.getLimiteContato().compareTo(valor) == -1){
                    throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE, this.getLimite().doubleValue()));
                }
            }
            else {
                if(this.getLimite().compareTo(valor) == -1){
                    throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE, this.getLimite().doubleValue()));
                }
            }
        }

        else{
            if(this.getSaldo().compareTo(valor) <= 0){
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }
        }

        this.saldo = saldo.subtract(valor);
        this.limite = limiteConta(conta, saldo);
        this.limiteContato = limiteContaContato(conta, saldo);
    }

    public int gerarNumeroConta() {
        return numeroContaAtual++;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "titular: " + titular +
                ", saldo: " + saldo +
                ", limite:" + limite +
                ", limiteContato:" + limiteContato +
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

    public BigDecimal getLimiteContato(){
        return limiteContato;
    }

    public void setLimiteContato(BigDecimal limiteContato) {
        this.limiteContato = limiteContato;
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

    public Set<Conta> getContatos() {
        return contatos;
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

    public static BigDecimal limiteContaContato(TipoConta tipo, BigDecimal saldo){

        BigDecimal limite_base = switch (tipo) {
            case COMUM -> saldo.multiply(new BigDecimal("0.8"));
            case SILVER -> saldo.multiply(new BigDecimal("1.1"));
            case GOLD -> saldo.multiply(new BigDecimal("2"));
            case DIAMOND -> saldo.multiply(new BigDecimal("4"));
        };

        BigDecimal limite_conta = saldo.add(limite_base);

        return limite_conta.max(BigDecimal.ZERO);
    }
}