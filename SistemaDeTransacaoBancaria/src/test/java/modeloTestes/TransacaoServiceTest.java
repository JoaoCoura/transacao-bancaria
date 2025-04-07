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
    public void depositar_ContaCriadaComSucessoESemSaldo_DeveAumentarSaldoDaContaDestino() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();
        Conta conta = ContaBuilder.novaConta().build();
        BigDecimal valorDeposito = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000));
        BigDecimal saldoConta = conta.getSaldo();

        // Act
        gerenciador.depositar(conta, valorDeposito);

        // Assert
        Assertions.assertEquals(saldoConta.add(valorDeposito), conta.getSaldo());
    }

    @Test
    public void depositar_ContaCriadaComSucessoEComSaldo_DeveAumentarSaldoDaContaDestino() //ERegistrarTransacao
    {
        // Arrange
        TransacaoService gerenciador = new TransacaoService();
        Conta conta = ContaBuilder.novaConta().comSaldo(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000))).build();
        BigDecimal valorDeposito = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000));
        BigDecimal saldoConta = conta.getSaldo();

        // Act
        gerenciador.depositar(conta, valorDeposito);

        // Assert
        Assertions.assertEquals(saldoConta.add(valorDeposito), conta.getSaldo());
    }
}