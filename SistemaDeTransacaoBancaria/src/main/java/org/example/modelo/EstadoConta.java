package org.example.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.util.MensagensTransacao.*;

public class EstadoConta {
    private BigDecimal saldo;
    private BigDecimal limite;
    private BigDecimal limiteContato;
    private TipoConta tipoConta;
    private TipoStatus status;
    private int pontos;
    private final List<Transacao> historico;
    private final Set<Conta> contatos;

    public EstadoConta() {
        this.saldo = BigDecimal.ZERO;
        this.tipoConta = TipoConta.COMUM;
        this.status = TipoStatus.ATIVA;
        this.historico = new ArrayList<>();
        this.contatos = new HashSet<>();
        this.pontos = 0;
        atualizarLimites();
    }

    private void atualizarLimites() {
        this.limite = calcularLimiteConta(tipoConta, saldo);
        this.limiteContato = calcularLimiteContato(tipoConta, saldo);
    }

    public void adicionarSaldo(BigDecimal valor) {
        validarValor(valor);
        this.saldo = saldo.add(valor);
        atualizarLimites();
    }

    public void subtrairSaldo(BigDecimal valor, TipoTransacao tipoTransacao, boolean isContato) {
        validarValor(valor);

        if (tipoTransacao == TipoTransacao.TRANSFERENCIA) {
            if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, this.getSaldo().doubleValue()));
            }
            if (isContato && limiteContato.compareTo(valor) < 0) {
                throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE, this.getLimiteContato().doubleValue()));
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
            throw new IllegalArgumentException(MENSAGEM_ERRO_VALOR_NEGATIVO);
        }
        if (valor.scale() > 2) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_CASAS_DECIMAIS);
        }
    }

    public static BigDecimal calcularLimiteConta(TipoConta tipo, BigDecimal saldo) {
        BigDecimal base = switch (tipo) {
            case COMUM -> saldo.multiply(new BigDecimal("0.5"));
            case SILVER -> saldo.multiply(new BigDecimal("0.8"));
            case GOLD -> saldo.multiply(new BigDecimal("1.1"));
            case DIAMOND -> saldo.multiply(new BigDecimal("2"));
        };
        return saldo.add(base).max(BigDecimal.ZERO);
    }

    public static BigDecimal calcularLimiteContato(TipoConta tipo, BigDecimal saldo) {
        BigDecimal base = switch (tipo) {
            case COMUM -> saldo.multiply(new BigDecimal("0.8"));
            case SILVER -> saldo.multiply(new BigDecimal("1.1"));
            case GOLD -> saldo.multiply(new BigDecimal("2"));
            case DIAMOND -> saldo.multiply(new BigDecimal("4"));
        };
        return saldo.add(base).max(BigDecimal.ZERO);
    }

    public void incrementarPontos(int pontos) {
        this.pontos += pontos;
    }

    public void verificarTipoConta(){
        if (pontos >= 1000) {
            setTipoConta(TipoConta.DIAMOND);
        } else if (pontos >= 500) {
            setTipoConta(TipoConta.GOLD);
        } else if (pontos >= 200) {
            setTipoConta(TipoConta.SILVER);
        } else {
            setTipoConta(TipoConta.COMUM);
        }

        this.limite = calcularLimiteConta(getTipoConta(), saldo);
        this.limiteContato = calcularLimiteContato(getTipoConta(), saldo);
    }

    public void ativarConta() {
        this.status = TipoStatus.ATIVA;
    }

    public void desativarConta() {
        this.status = TipoStatus.INATIVA;
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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public TipoStatus getStatus() {
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

    public void setTipoConta(TipoConta tipoConta){
        this.tipoConta = tipoConta;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setStatus(TipoStatus status) {
        this.status = status;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public void setLimiteContato(BigDecimal limiteContato) {
        this.limiteContato = limiteContato;
    }
}