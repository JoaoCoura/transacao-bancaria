package modeloTestes;

import org.example.modelo.Cliente;
import org.example.modelo.CadastroCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
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
    public void criarCliente_SemNome_DeveRetornarErroSobreNomeNulo()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = null;
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = cadastro.cadastrarCliente(nome, cpf, email);
        });

        // Assert
        Assertions.assertEquals("Nome não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void criarCliente_SemCPF_DeveRetornarErroSobreCPFNulo()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = null;
        String email = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = cadastro.cadastrarCliente(nome, cpf, email);
        });

        // Assert
        Assertions.assertEquals("CPF não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void criarCliente_SemEmail_DeveRetornarErroSobreEmailNulo()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = gerarCpfValido();
        String email = null;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = cadastro.cadastrarCliente(nome, cpf, email);
        });

        // Assert
        Assertions.assertEquals("Email não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void criarCliente_ComEmailSemFormatacao_DeveRetornarErroSobreEmailComFormatacaoErrada()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();;
        String cpf = gerarCpfValido();
        String emailErrado = faker.name().fullName();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = cadastro.cadastrarCliente(nome, cpf, emailErrado);
        });

        // Assert
        Assertions.assertEquals("Email está em formato inválido.", exception.getMessage());
    }

    @Test
    public void criarCliente_CPFComMenosDe11Digitos_DeveRetornarErroSobreCPFComFormatacaoErrada()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpfErrado = faker.number().toString();
        String email = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente cliente = cadastro.cadastrarCliente(nome, cpfErrado, email);
        });

        // Assert
        Assertions.assertEquals("CPF deve conter exatamente 11 dígitos numéricos.", exception.getMessage());
    }

    @Test
    public void criarCliente_CPFJaCadastrado_DeveRetornarErroSobreCPFJaCadastrado()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        Cliente cliente = cadastro.cadastrarCliente(nome, cpf, email);

        String outroNome = faker.name().fullName();
        String mesmoCpf = cpf;
        String outroEmail = faker.internet().emailAddress();

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente novoCliente = cadastro.cadastrarCliente(outroNome, mesmoCpf, outroEmail);
        });

        // Assert
        Assertions.assertEquals("CPF já cadastrado.", exception.getMessage());
    }

    @Test
    public void criarCliente_EmailJaCadastrado_DeveRetornarErroSobreEmailJaCadastrado()
    {
        // Arrange
        CadastroCliente cadastro = new CadastroCliente();

        String nome = faker.name().fullName();
        String cpf = gerarCpfValido();
        String email = faker.internet().emailAddress();

        Cliente cliente = cadastro.cadastrarCliente(nome, cpf, email);

        String outroNome = faker.name().fullName();
        String outroCPF = gerarCpfValido();
        String mesmoEmail = email;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Cliente novoCliente = cadastro.cadastrarCliente(outroNome, outroCPF, mesmoEmail);
        });

        // Assert
        Assertions.assertEquals("Email já cadastrado.", exception.getMessage());
    }
}
