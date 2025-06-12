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
    private Categoria conta;
    private Status status;
    private int numeroConta;
    private final List<Transacao> historico;
    private final Set<Conta> contatos;
    private Categoria categoria;
    private int pontos;

    private ContaBuilder() {
        this.titular = ClienteBuilder.novoCliente().build();
        this.saldo = BigDecimal.ZERO;
        this.limite = BigDecimal.ZERO;
        this.limiteContato = BigDecimal.ZERO;
        this.conta = Categoria.COMUM;
        this.status = Status.ATIVA;
        this.numeroConta = new Conta(titular).getNumeroConta();
        this.historico = new ArrayList<>();
        this.contatos = new HashSet<>();
        this.categoria = Categoria.COMUM;
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
        this.status = Status.ATIVA;
        return this;
    }

    public ContaBuilder comStatusInativa() {
        this.status = Status.INATIVA;
        return this;
    }

    public ContaBuilder comTipoComum() {
        this.conta = Categoria.COMUM;
        return this;
    }

    public ContaBuilder comTipoSilver() {
        this.conta = Categoria.SILVER;
        return this;
    }

    public ContaBuilder comTipoGold() {
        this.conta = Categoria.GOLD;
        return this;
    }

    public ContaBuilder comTipoDiamond() {
        this.conta = Categoria.DIAMOND;
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
        conta.setCategoria(categoria);
        return conta;
    }
}