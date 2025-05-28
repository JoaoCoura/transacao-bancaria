package builder;

import org.example.modelo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ContaBuilder {

    private Cliente titular;
    private BigDecimal saldo;
    private BigDecimal limite;
    private BigDecimal limiteContato;
    private TipoConta conta;
    private TipoStatus status;
    private int numeroConta;
    private final List<Transacao> historico;
    private final Set<Conta> contatos;
    private TipoConta tipo;
    private int pontos;

    private ContaBuilder() {
        this.titular = ClienteBuilder.novoCliente().build();
        this.saldo = BigDecimal.ZERO;
        this.limite = BigDecimal.ZERO;
        this.limiteContato = BigDecimal.ZERO;
        this.conta = TipoConta.COMUM;
        this.status = TipoStatus.ATIVA;
        this.numeroConta = new Conta(titular).getNumeroConta();
        this.historico = new ArrayList<>();
        this.contatos = new HashSet<>();
        this.tipo = TipoConta.COMUM;
        this.pontos = 0;
    }

    public static ContaBuilder novaConta() {
        return new ContaBuilder();
    }

    public ContaBuilder comTitular(Cliente titular) {
        this.titular = titular;
        return this;
    }

    public ContaBuilder comSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public ContaBuilder comNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
        return this;
    }

    public ContaBuilder semTitular() {
        this.titular = null;
        return this;
    }

    public ContaBuilder comStatusAtiva() {
        this.status = TipoStatus.ATIVA;
        return this;
    }

    public ContaBuilder comStatusInativa() {
        this.status = TipoStatus.INATIVA;
        return this;
    }

    public ContaBuilder comTipoComum() {
        this.conta = TipoConta.COMUM;
        return this;
    }

    public ContaBuilder comTipoSilver() {
        this.conta = TipoConta.SILVER;
        return this;
    }

    public ContaBuilder comTipoGold() {
        this.conta = TipoConta.GOLD;
        return this;
    }

    public ContaBuilder comTipoDiamond() {
        this.conta = TipoConta.DIAMOND;
        return this;
    }

    public ContaBuilder comContato(Conta contato)
    {
        this.contatos.add(contato);
        return this;
    }

    public ContaBuilder comPontos(int pontos) {
        this.pontos = pontos;
        return this;
    }

    public Conta build() {
        Conta conta = new Conta(titular);
        conta.setSaldo(saldo);
        conta.setStatus(status);
        conta.setLimite(EstadoConta.calcularLimiteConta(this.conta, saldo));
        conta.setLimiteContato(EstadoConta.calcularLimiteContato(this.conta, saldo));
        conta.setTipoConta(tipo);
        return conta;
    }
}