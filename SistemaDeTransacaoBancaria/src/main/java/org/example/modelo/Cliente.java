package org.example.modelo;
import static org.example.util.Validator.*;

public class Cliente {

    private String nome;
    private String cpf;
    private String email;

    public Cliente(String nome, String cpf, String email) {

        requerNaoNulo(nome, "Nome");
        requerCpfValido(cpf, "CPF");
        requerEmailValido(email, "Email");

        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente [nome: " + nome + ", cpf: " + cpf + ", email: " + email + "]";
    }
}
