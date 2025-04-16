package modeloTestes;

import builder.ContaBuilder;
import org.example.modelo.Conta;
import org.example.modelo.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.example.modelo.TransacaoService.*;

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
    public void depositar_ContaAtivaEComSaldo_DeveAumentarSaldoDaContaDestino() //ERegistrarTransacao
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
    public void depositar_ContaInativa_DeveRetornarErroSobreContaInativaENaoRealizarOperacao()
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
        Assertions.assertEquals(MENSAGEM_ERRO_DEPOSITAR_CONTA_INATIVA, exception.getMessage());
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
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_VALOR_NEGATIVO, "o", "depósito")), exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void depositar_ValorDepositoComMaisDeDuasCasasDecimais_DeveRetornarErroSobreCasasDecimaisENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorDeposito = BigDecimal
                .valueOf(faker.number().randomDouble(2, 0, 1000))
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.depositar(conta, valorDeposito);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_CASAS_DECIMAIS, "o", "depósito")), exception.getMessage());
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
    public void sacar_ContaInativa_DeveRetornarErroSobreContaInativaENaoRealizarOperacao()
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
        Assertions.assertEquals(MENSAGEM_ERRO_SACAR_CONTA_INATIVA, exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_ContaSemSaldo_DeveRetornarErroSobreSaldoInsuficienteENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = conta.getSaldo().add(BigDecimal.ONE);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, conta.getSaldo().doubleValue())), exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_SaldoInsuficiente_DeveRetornarErroSobreSaldoInsuficienteENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 500)))
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = conta.getSaldo().add(BigDecimal.ONE);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, conta.getSaldo().doubleValue())), exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_ValorSaqueNaoPositivo_DeveRetornarErroSobreValorDepositoNaoPositivoENaoRealizarOperacao()
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
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_VALOR_NEGATIVO, "o", "saque")), exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void sacar_ValorSaqueComMaisDeDuasCasasDecimais_DeveRetornarErroSobreCasasDecimaisENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta conta = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoConta = conta.getSaldo();

        BigDecimal valorSaque = BigDecimal
                .valueOf(faker.number().randomDouble(2, 0, 1000))
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.sacar(conta, valorSaque);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_CASAS_DECIMAIS, "o", "saque")), exception.getMessage());
        Assertions.assertEquals(saldoConta, conta.getSaldo());
    }

    @Test
    public void transferir_ContaOrigemComSaldoSuficienteEContasAtivas_DeveAumentarSaldoDaContaDestinoEDiminuirDaContaOrigem()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 500)))
                .build();

        Conta contaDestino = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
        BigDecimal saldoContaDestino = contaDestino.getSaldo();

        BigDecimal valorTransferencia = contaOrigem.getSaldo().subtract(BigDecimal.ONE);

        // Act
        gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);

        // Assert
        Assertions.assertEquals(saldoContaOrigem.subtract(valorTransferencia), contaOrigem.getSaldo());
        Assertions.assertEquals(saldoContaDestino.add(valorTransferencia), contaDestino.getSaldo());
    }

    @Test
    public void transferir_ContaOrigemEContaDestinoIguais_DeveRetornarErroSobreMesmaContaENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 500)))
                .build();

        Conta contaDestino = contaOrigem;

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();

        BigDecimal valorTransferencia = contaOrigem.getSaldo().subtract(BigDecimal.ONE);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);
        });

        // Assert
        Assertions.assertEquals(MENSAGEM_ERRO_PROPRIA_CONTA, exception.getMessage());
        Assertions.assertEquals(saldoContaOrigem, contaOrigem.getSaldo());
    }

    @Test
    public void transferir_ContaOrigemInativa_DeveRetornarErroSobreTransferirDeContaInativaENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .comStatusInativa()
                .build();

        Conta contaDestino = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
        BigDecimal saldoContaDestino = contaDestino.getSaldo();

        BigDecimal valorTransferencia = contaOrigem.getSaldo().subtract(BigDecimal.ONE);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);
        });

        // Assert
        Assertions.assertEquals(MENSAGEM_ERRO_CONTA_INATIVA_ORIGEM, exception.getMessage());
        Assertions.assertEquals(saldoContaOrigem, contaOrigem.getSaldo());
        Assertions.assertEquals(saldoContaDestino, contaDestino.getSaldo());
    }

    @Test
    public void transferir_ContaDestinoInativa_DeveRetornarErroSobreTransferirParaContaInativaENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .build();

        Conta contaDestino = ContaBuilder
                .novaConta()
                .comStatusInativa()
                .build();

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
        BigDecimal saldoContaDestino = contaDestino.getSaldo();

        BigDecimal valorTransferencia = contaOrigem.getSaldo().subtract(BigDecimal.ONE);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);
        });

        // Assert
        Assertions.assertEquals(MENSAGEM_ERRO_CONTA_INATIVA_DESTINO, exception.getMessage());
        Assertions.assertEquals(saldoContaOrigem, contaOrigem.getSaldo());
        Assertions.assertEquals(saldoContaDestino, contaDestino.getSaldo());
    }

    @Test
    public void transferir_ValorTransferenciaNaoPositivo_DeveRetornarErroSobreValorTransferenciaNaoPositivoENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .build();

        Conta contaDestino = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
        BigDecimal saldoContaDestino = contaDestino.getSaldo();

        BigDecimal valorTransferencia = BigDecimal
                .valueOf(faker.number().randomDouble(2, 0, 1000))
                .negate();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_VALOR_NEGATIVO, "a", "transferência")), exception.getMessage());
        Assertions.assertEquals(saldoContaOrigem, contaOrigem.getSaldo());
        Assertions.assertEquals(saldoContaDestino, contaDestino.getSaldo());
    }

    @Test
    public void transferir_ContaSemSaldo_DeveRetornarErroSobreSaldoInsuficienteENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .build();

        Conta contaDestino = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
        BigDecimal saldoContaDestino = contaDestino.getSaldo();

        BigDecimal valorTransferencia = contaOrigem.getSaldo().add(BigDecimal.ONE);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, contaOrigem.getSaldo().doubleValue())), exception.getMessage());
        Assertions.assertEquals(saldoContaOrigem, contaOrigem.getSaldo());
        Assertions.assertEquals(saldoContaDestino, contaDestino.getSaldo());
    }

    @Test
    public void transferir_LimiteInsuficiente_DeveRetornarErroSobreLimiteInsuficienteENaoRealizarOperacao()
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();

        Conta contaOrigem = ContaBuilder
                .novaConta()
                .comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 500)))
                .build();

        Conta contaDestino = ContaBuilder
                .novaConta()
                .build();

        BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
        BigDecimal saldoContaDestino = contaDestino.getSaldo();

        BigDecimal valorTransferencia = contaOrigem.getSaldo().multiply(BigDecimal.TWO);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerenciador.transferir(contaOrigem, contaDestino, valorTransferencia);
        });

        // Assert
        Assertions.assertEquals((String.format(MENSAGEM_ERRO_LIMITE, contaOrigem.getLimite().doubleValue())), exception.getMessage());
        Assertions.assertEquals(saldoContaOrigem, contaOrigem.getSaldo());
        Assertions.assertEquals(saldoContaDestino, contaDestino.getSaldo());
    }
}