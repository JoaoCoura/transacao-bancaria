package org.example.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.util.MensagensTransacao.*;

public class EstadoConta {
    private BigDecimal saldo;
    private BigDecimal limite;
    private BigDecimal limiteContato;
    private Categoria categoria;
    private Status status;
    private int pontos;
    private List<Transacao> historico;
    private Set<Conta> contatos;

    public EstadoConta() {
        this.saldo = BigDecimal.ZERO;
        this.categoria = Categoria.COMUM;
        this.status = Status.ATIVA;
        this.historico = new ArrayList<>();
        this.contatos = new HashSet<>();
        this.pontos = 0;
        atualizarLimites();
    }

    private void atualizarLimites() {
        this.limite = calcularLimiteConta(categoria, saldo);
        this.limiteContato = calcularLimiteContato(categoria, saldo);
    }

    public void adicionarSaldo(BigDecimal valor) {
        validarValor(valor);
        this.saldo = saldo.add(valor);
        atualizarLimites();
    }

    public void subtrairSaldo(BigDecimal valor, Operacao operacao, boolean isContato) {
        validarValor(valor);

        if (operacao == Operacao.TRANSFERENCIA) {
            if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }
            if (isContato && limiteContato.compareTo(valor) < 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE_CONTATO, this.getLimiteContato().doubleValue()));
            } else if (!isContato && limite.compareTo(valor) < 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE, this.getLimite().doubleValue()));
            }
        } else {
            if (saldo.compareTo(valor) < 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }
        }

        this.saldo = saldo.subtract(valor);
        atualizarLimites();
    }

    private void validarValor(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_VALOR_NAO_POSITIVO);
        }
        if (valor.scale() > 2) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_CASAS_DECIMAIS);
        }
    }

    public static BigDecimal calcularLimiteConta(Categoria categoria, BigDecimal saldoAtual) {
        BigDecimal base = switch (categoria) {
            case COMUM -> saldoAtual.multiply(new BigDecimal("0.5"));
            case SILVER -> saldoAtual.multiply(new BigDecimal("0.8"));
            case GOLD -> saldoAtual.multiply(new BigDecimal("1.1"));
            case DIAMOND -> saldoAtual.multiply(new BigDecimal("2"));
        };
        return saldoAtual.add(base).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcularLimiteContato(Categoria categoria, BigDecimal saldoAtual) {
        BigDecimal base = switch (categoria) {
            case COMUM -> saldoAtual.multiply(new BigDecimal("0.8"));
            case SILVER -> saldoAtual.multiply(new BigDecimal("1.1"));
            case GOLD -> saldoAtual.multiply(new BigDecimal("2"));
            case DIAMOND -> saldoAtual.multiply(new BigDecimal("4"));
        };
        return saldoAtual.add(base).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    public void incrementarPontos(int pontos) {
        this.pontos += pontos;
    }

    public void verificarTipoConta(){
        if (pontos >= 1000) {
            setCategoria(Categoria.DIAMOND);
        } else if (pontos >= 500) {
            setCategoria(Categoria.GOLD);
        } else if (pontos >= 200) {
            setCategoria(Categoria.SILVER);
        } else {
            setCategoria(Categoria.COMUM);
        }

        this.limite = calcularLimiteConta(getCategoria(), saldo);
        this.limiteContato = calcularLimiteContato(getCategoria(), saldo);
    }

    public void ativarConta() {
        this.status = Status.ATIVA;
    }

    public void desativarConta() {
        this.status = Status.INATIVA;
    }

    public void adicionarContato(Conta conta) {
        boolean adicionado = this.contatos.add(conta);
        if (adicionado) {
            System.out.println("Contato adicionado com sucesso.");
        } else {
            System.out.println("Contato já existe.");
        }
    }

    public void removerContato(Conta conta) {
        boolean removido = this.contatos.remove(conta);
        if (removido) {
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    public void adicionarTransacao(Transacao transacao) {
        historico.add(transacao);
    }


    public void imprimirHistoricoTransacoes() {
        historico.forEach(System.out::println);
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public BigDecimal getLimiteContato() {
        return limiteContato;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Status getStatus() {
        return status;
    }

    public int getPontos() {
        return pontos;
    }

    public List<Transacao> getHistorico() {
        return historico;
    }

    public Set<Conta> getContatos() {
        return contatos;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public void setLimiteContato(BigDecimal limiteContato) {
        this.limiteContato = limiteContato;
    }
}