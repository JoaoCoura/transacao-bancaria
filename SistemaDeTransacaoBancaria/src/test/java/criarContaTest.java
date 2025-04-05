import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.example.modelo.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class criarContaTest {

    @Test
    public void criarConta_InformacoesValidasInseridas_DeveRetornarContaCriadaComSaldoZeroEAtiva()
    {
        // Arrange
        Cliente cliente = new Cliente("Fuba", "123456789", "fubs@yahoo.com");

        // Act
        Conta conta = new Conta(cliente);

        // Assert
        Assertions.assertNotNull(conta);
        Assertions.assertEquals("Fuba", conta.getTitular().getNome());
        Assertions.assertEquals("123456789", conta.getTitular().getCpf());
        Assertions.assertEquals("fubs@yahoo.com", conta.getTitular().getEmail());
        Assertions.assertEquals(BigDecimal.ZERO, conta.getSaldo());
        Assertions.assertEquals(Status.ATIVA, conta.getStatus());

    }


}
