package modeloTestes;

import builder.ContaBuilder;
import org.example.modelo.Conta;
import org.example.modelo.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class TransacaoServiceTest {

    private static final Faker faker = new Faker();

    @Test
    public void depositar_ContaAtivaESemSaldo_DeveAumentarSaldoDaContaDestino() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorDeposito = BigDecimal
                .valueOf(faker.number().randomDouble(2, 1, 1000));


        // Act
        gerenciador.depositar(conta, valorDeposito);

        // Assert
        Assertions.assertEquals(saldoConta.add(valorDeposito), conta.getSaldo());
    }

    @Test
    public void depositar_ContaAtivaEComSucessoEComSaldo_DeveAumentarSaldoDaContaDestino() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal
                        .valueOf(faker.number().randomDouble(2, 0, 1000))).build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorDeposito = BigDecimal
                .valueOf(faker.number().randomDouble(2, 1, 1000));


        // Act
        gerenciador.depositar(conta, valorDeposito);

        // Assert
        Assertions.assertEquals(saldoConta.add(valorDeposito), conta.getSaldo());
    }

    @Test
    public void depositar_ContaInativaEComSucessoEComSaldo_DeveRetornarErroSobreContaInativaENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .comStatusInativa()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorDeposito = BigDecimal
                .valueOf(faker.number().randomDouble(2, 1, 1000));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.depositar(conta, valorDeposito);
        });

        // Assert
        Assertions.assertEquals("Não é possível depositar em uma conta inativa.", exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void depositar_ValorDepositoNaoPositivo_DeveRetornarErroSobreValorDepositoNaoPositivoENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorDeposito = BigDecimal
                .valueOf(faker.number().randomDouble(2, 0, 1000))
                .negate();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.depositar(conta, valorDeposito);
        });

        // Assert
        Assertions.assertEquals("Valor do depósito deve ser positivo.", exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_ContaAtivaEComSaldoSuficiente_DeveDiminuirSaldoDaContaOrigem() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 500, 1000)))
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 500));

        // Act
        gerenciador.sacar(conta, valorSaque);

        // Assert
        Assertions.assertEquals(saldoConta.subtract(valorSaque), conta.getSaldo());
    }

    @Test
    public void sacar_ContaInativa_DeveRetornarErroSobreContaInativaENaoRealizarOperacao() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .comStatusInativa()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 500));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals("Não é possível sacar de uma conta inativa.", exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_ContaSemSaldo_DeveRetornarErroSobreSaldoInsuficienteENaoRealizarOperacao() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 500));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals("Saldo insuficiente.", exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_SaldoInsuficiente_DeveRetornarErroSobreSaldoInsuficienteENaoRealizarOperacao() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 500)))
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = BigDecimal.valueOf(faker.number().randomDouble(2, 501, 1000));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals("Saldo insuficiente.", exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_ValorSaqueNaoPositivo_DeveRetornarErroSobreValorDepositoNaoPositivoENaoRealizarOperacao() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = BigDecimal
                .valueOf(faker.number().randomDouble(2, 0, 1000))
                .negate();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals("Valor do saque deve ser positivo.", exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }
}