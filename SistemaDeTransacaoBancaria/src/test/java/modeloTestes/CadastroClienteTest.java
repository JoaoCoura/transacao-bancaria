package modeloTestes;

import org.example.modelo.Cliente;
import org.example.modelo.CadastroCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import java.util.UUID;
import static org.example.util.CpfUtil.gerarCpfValido;

public class CadastroClienteTest {

    private static final Faker faker = new Faker();

    @Test
    public void cadastrarCliente_InformacoesValidasInseridas_DeveRetornarClienteCriadoComInformacoesInseridas()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        // Act
        Cliente cliente = cadastro.cadastrarCliente(nome, cpf, email);

        // Assert
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(nome, cliente.getNome());
        Assertions.assertEquals(cpf, cliente.getCpf());
        Assertions.assertEquals(email, cliente.getEmail());
    }

    @Test
    public void cadastrarCliente_SemNome_DeveRetornarErroSobreNomeNulo()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = null;
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(nome, cpf, email);
        });

        // Assert
        Assertions.assertEquals("Nome não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_SemCPF_DeveRetornarErroSobreCPFNulo()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = null;
        String email = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(nome, cpf, email);
        });

        // Assert
        Assertions.assertEquals("CPF não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_SemEmail_DeveRetornarErroSobreEmailNulo()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = gerarCpfValido();
        String email = null;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(nome, cpf, email);
        });

        // Assert
        Assertions.assertEquals("Email não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_ComEmailComMaisDe257Caracteres_DeveRetornarErroSobreEmailComFormatacaoErrada()
    {
        // Arrange
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 258) {
            sb.append(UUID.randomUUID());
        }

        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = gerarCpfValido();
        String emailErrado = sb.substring(0, 258);

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(nome, cpf, emailErrado);
        });

        // Assert
        Assertions.assertEquals("Email deve ter no máximo 257 caracteres.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_ComEmailSemFormatacao_DeveRetornarErroSobreEmailComFormatacaoErrada()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = gerarCpfValido();
        String emailErrado = faker.name().fullName();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(nome, cpf, emailErrado);
        });

        // Assert
        Assertions.assertEquals("Email está em formato inválido.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_CPFComMenosDe11Digitos_DeveRetornarErroSobreCPFComFormatacaoErrada()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpfErrado = faker.number().toString();
        String email = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(nome, cpfErrado, email);
        });

        // Assert
        Assertions.assertEquals("CPF deve conter exatamente 11 dígitos numéricos.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_CPFJaCadastrado_DeveRetornarErroSobreCPFJaCadastrado()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        cadastro.cadastrarCliente(nome, cpf, email);

        String outroNome = faker.name().fullName();
        String mesmoCpf = cpf;
        String outroEmail = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(outroNome, mesmoCpf, outroEmail);
        });

        // Assert
        Assertions.assertEquals("CPF já cadastrado.", exception.getMessage());
    }

    @Test
    public void cadastrarCliente_EmailJaCadastrado_DeveRetornarErroSobreEmailJaCadastrado()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        cadastro.cadastrarCliente(nome, cpf, email);

        String outroNome = faker.name().fullName();
        String outroCPF = gerarCpfValido();
        String mesmoEmail = email;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cadastro.cadastrarCliente(outroNome, outroCPF, mesmoEmail);
        });

        // Assert
        Assertions.assertEquals("Email já cadastrado.", exception.getMessage());
    }
}
