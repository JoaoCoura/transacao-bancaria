package builder;

import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.example.modelo.TipoStatus;
import java.math.BigDecimal;

public final class ContaBuilder {

    private Cliente titular;
    private BigDecimal saldo;
    private TipoStatus status;
    private int numeroConta;

    private ContaBuilder() {
        this.titular = ClienteBuilder.novoCliente().build();
        this.saldo = BigDecimal.ZERO;
        this.status = TipoStatus.ATIVA;
        this.numeroConta = new Conta(titular).getNumeroConta(); // Gera um número aleatório válido
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

    public ContaBuilder comContaAtiva() {
        this.status = TipoStatus.ATIVA;
        return this;
    }

    public ContaBuilder comContaInativa() {
        this.status = TipoStatus.INATIVA;
        return this;
    }

    public Conta build() {
        Conta conta = new Conta(titular);
        conta.setSaldo(saldo);
        conta.setStatus(status);
        conta.setNumeroConta(numeroConta);
        return conta;
    }
}
