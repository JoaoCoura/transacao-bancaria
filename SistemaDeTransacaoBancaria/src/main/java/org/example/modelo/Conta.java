package org.example.modelo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Conta {
    private static int numeroContaAtual = 1;
    private DescricaoConta descricao;
    private EstadoConta estado;


    public Conta(Cliente titular) {
        int numeroConta = gerarNumeroConta();
        this.descricao = new DescricaoConta(titular, numeroConta);
        this.estado = new EstadoConta();
    }

    private int gerarNumeroConta() {
        return numeroContaAtual++;
    }

    public void adicionarSaldo(BigDecimal valor) {
        estado.adicionarSaldo(valor);
    }

    public void subtrairSaldo(BigDecimal valor, TipoTransacao tipoTransacao, boolean isContato) {
        estado.subtrairSaldo(valor, tipoTransacao, isContato);
    }

    public void adicionarTransacao(Transacao transacao) {
        estado.adicionarTransacao(transacao);
    }

    public void imprimirHistoricoTransacoes() {
        estado.imprimirHistoricoTransacoes();
    }

    public void incrementarPontos(int pontos) {
        estado.incrementarPontos(pontos);
    }

    public void adicionarContato(Conta conta) {
        estado.adicionarContato(conta);
    }

    public void removerContato(Conta conta) {
        estado.removerContato(conta);
    }

    public void mostrarContatos() {
        for (Conta conta : estado.getContatos()) {
            System.out.println(conta);
        }
    }

    public void ativarConta() {
        estado.ativarConta();
    }

    public void desativarConta() {
        estado.desativarConta();
    }

    public static BigDecimal calcularLimiteConta(TipoConta tipo, BigDecimal saldo){
        return EstadoConta.calcularLimiteConta(tipo, saldo);
    }

    public static BigDecimal calcularLimiteContato(TipoConta tipo, BigDecimal saldo){
        return EstadoConta.calcularLimiteContato(tipo, saldo);
    }

    public Cliente getTitular() {
        return descricao.getTitular();
    }

    public int getNumeroConta() {
        return descricao.getNumeroConta();
    }

    public TipoStatus getStatus(){
        return estado.getStatus();
    }

    public EstadoConta getEstado() {
        return estado;
    }

    public BigDecimal getSaldo(){
        return estado.getSaldo();
    }

    public void verificarTipoConta(){
        estado.verificarTipoConta();
    }

    public Set<Conta> getContatos() {
        return estado.getContatos();
    }

    public BigDecimal getLimite(){
        return estado.getLimite();
    }

    public BigDecimal getLimiteContato(){
        return estado.getLimiteContato();
    }

    public void setTipoConta(TipoConta tipoConta){
        this.estado.setTipoConta(tipoConta);
    }

    public List<Transacao> getHistorico() {
        return estado.getHistorico();
    }

    public int getPontos()
    {
        return estado.getPontos();
    }

    public void setSaldo(BigDecimal saldo) {
        this.estado.setSaldo(saldo);
    }

    public void setStatus(TipoStatus status) {
        this.estado.setStatus(status);
    }

    public void setLimite(BigDecimal limite) {
        this.estado.setLimite(limite);
    }

    public void setLimiteContato(BigDecimal limiteContato) {
        this.estado.setLimiteContato(limiteContato);
    }

    @Override
    public String toString() {
        return "Conta{" + descricao.getTitular() +
                ", numeroConta: " + descricao.getNumeroConta() +
                ", saldo: " + estado.getSaldo() +
                ", limite: " + estado.getLimite() +
                ", limiteContato: " + estado.getLimiteContato() +
                ", status: " + estado.getStatus() +
                ", tipo: " + estado.getTipoConta() +
                ", pontos: " + estado.getPontos() +
                '}';
    }
}