import org.example.modelo.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class criarClienteTest {

    @Test
    public void CriarCliente_InformacoesValidasInseridas_DeveRetornarContaCriadaComSaldoZeroEAtiva(){
        // Criando um cliente
        Cliente cliente = new Cliente("João Silva", "12345678901", "joao@email.com");

        // Verificando se os dados estão corretos
        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("joao@email.com", cliente.getEmail());
    }

    //fzr dps pra criar cliente com bgl duplicado (cpf e email)


}
