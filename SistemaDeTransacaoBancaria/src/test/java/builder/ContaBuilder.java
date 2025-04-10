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
    private TipoConta tipo;
    private TipoStatus status;
    private int numeroConta;

    private ContaBuilder() {
        this.titular = ClienteBuilder.novoCliente().build();
        this.saldo = BigDecimal.ZERO;
        this.limite = BigDecimal.ZERO;
        this.tipo = TipoConta.COMUM;
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

    public Conta build() {
        Conta conta = new Conta(titular);
        conta.setSaldo(saldo);
        conta.setStatus(status);
        conta.setNumeroConta(numeroConta);
        conta.setLimite(limiteConta(tipo, saldo));
        return conta;
    }

    private BigDecimal limiteConta(TipoConta tipo, BigDecimal saldo){

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

        BigDecimal limite_conta = saldo.add(limite_base);

        return limite_conta.max(BigDecimal.ZERO);
    }
}