package modeloTestes;

import org.example.modelo.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import builder.ClienteBuilder;
import com.github.javafaker.Faker;
import static org.example.util.CpfUtil.gerarCpfValido;

public class ClienteTest {

    private static final Faker faker = new Faker();

    @Test
    public void criarCliente_InformacoesValidasInseridas_DeveRetornarClienteCriadoComInformacoesInseridas()
    {
        // Arrange
        String nome = faker.name().fullName();
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        // Act
        Cliente cliente = ClienteBuilder
                .novoCliente()
                .comNome(nome)
                .comCpf(cpf)
                .comEmail(email)
                .build();


        // Assert
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(nome, cliente.getNome());
        Assertions.assertEquals(cpf, cliente.getCpf());
        Assertions.assertEquals(email, cliente.getEmail());
    }

    @Test
    public void criarCliente_SemNome_DeveRetornarErroSobreNomeNulo()
    {
        // Arrange & Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Cliente cliente = ClienteBuilder
                .novoCliente()
                .semNome()
                .build();
        });

        // Assert
        Assertions.assertEquals("Nome não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void criarCliente_SemCPF_DeveRetornarErroSobreCPFNulo()
    {
        // Arrange & Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = ClienteBuilder
                    .novoCliente()
                    .semCpf()
                    .build();
        });

        // Assert
        Assertions.assertEquals("CPF não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void criarCliente_SemEmail_DeveRetornarErroSobreEmailNulo()
    {
        // Arrange & Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = ClienteBuilder
                    .novoCliente()
                    .semEmail()
                    .build();
        });

        // Assert
        Assertions.assertEquals("Email não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void criarCliente_ComEmailSemFormatacao_DeveRetornarErroSobreEmailComFormatacaoErrada()
    {
        // Arrange
        String emailErrado = faker.name().fullName();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = ClienteBuilder
                    .novoCliente()
                    .comEmail(emailErrado)
                    .build();
        });

        // Assert
        Assertions.assertEquals("Email está em formato inválido.", exception.getMessage());
    }

    @Test
    public void criarCliente_CPFComMenosDe11Digitos_DeveRetornarErroSobreCPFComFormatacaoErrada()
    {
        // Arrange
        String cpfErrado = faker.number().toString();


        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = ClienteBuilder
                    .novoCliente()
                    .comCpf(cpfErrado)
                    .build();
        });

        // Assert
        Assertions.assertEquals("CPF deve conter exatamente 11 dígitos numéricos.", exception.getMessage());
    }
}
