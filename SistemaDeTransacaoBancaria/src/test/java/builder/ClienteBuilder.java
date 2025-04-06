package builder;

import org.example.modelo.Cliente;
import com.github.javafaker.Faker;
import static org.example.util.CpfUtil.gerarCpfValido;

public final class ClienteBuilder {

    private String nome;
    private String cpf;
    private String email;

    private static final Faker faker = new Faker();

    private ClienteBuilder() {
        this.nome = faker.name().fullName();
        this.cpf = gerarCpfValido();
        this.email = faker.internet().emailAddress();
    }

    public static ClienteBuilder novoCliente() {
        return new ClienteBuilder();
    }

    public ClienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public ClienteBuilder semNome() {
        this.nome = null;
        return this;
    }

    public ClienteBuilder semCpf() {
        this.cpf = null;
        return this;
    }

    public ClienteBuilder semEmail() {
        this.email = null;
        return this;
    }

    public Cliente build() {
        return new Cliente(nome, cpf, email);
    }
}
