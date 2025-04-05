import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaqueTests {
    private Conta conta;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Fuba", "123456789", "fubs@yahoo.com");
        conta = new Conta(cliente);
    }
    /*
    @Test
    public void testSaqueComSucesso(){
        conta.depositar(new BigDecimal("500"));
        conta.sacar(new BigDecimal("200"));
        Assertions.assertEquals(new BigDecimal("300"), conta.getSaldo());
    }

    @Test
    void testSaqueSaldoInsuficiente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                conta.sacar(new BigDecimal("100"))
        );
        Assertions.assertEquals("Saldo insuficiente", exception.getMessage());
    }

    @Test
    void testSaqueValorNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                conta.sacar(new BigDecimal("-50"))
        );
        Assertions.assertEquals("Valor do saque deve ser positivo.", exception.getMessage());
    }

    @Test
    void testSaqueZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                conta.sacar(BigDecimal.ZERO)
        );
        Assertions.assertEquals("Valor do saque deve ser positivo.", exception.getMessage());
    }

     */



}
