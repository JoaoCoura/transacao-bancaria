package builder;

import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.example.modelo.TipoConta;
import org.example.modelo.TipoStatus;
import java.math.BigDecimal;

public final class ContaBuilder {

    private Cliente titular;
    private BigDecimal saldo;
    private BigDecimal limite;
    private TipoConta conta;
    private TipoStatus status;
    private int numeroConta;

    private ContaBuilder() {
        this.titular = ClienteBuilder.novoCliente().build();
        this.saldo = BigDecimal.ZERO;
        this.limite = BigDecimal.ZERO;
        this.conta = TipoConta.COMUM;
        this.status = TipoStatus.ATIVA;
        this.numeroConta = new Conta(titular).getNumeroConta();
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

    public Conta build() {
        Conta conta = new Conta(titular);
        conta.setSaldo(saldo);
        conta.setStatus(status);
        conta.setNumeroConta(numeroConta);
        conta.setLimite(Conta.limiteConta(this.conta, saldo));
        return conta;
    }
}