package org.example.modelo;

import java.util.HashMap;

import static org.example.util.Validator.*;

public class CadastroCliente {
    private HashMap<String, Cliente> clientesPorCpf = new HashMap<>();
    private HashMap<String, Cliente> clientesPorEmail = new HashMap<>();

    public Cliente cadastrarCliente(String nome, String cpf, String email) {
        verificarNaoNulo(nome, "Nome");
        verificarCpfValido(cpf, "CPF");
        verificarEmailValido(email, "Email");

        if (clientesPorCpf.containsKey(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        if (clientesPorEmail.containsKey(email)) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        Cliente cliente = new Cliente(nome, cpf, email);
        clientesPorCpf.put(cliente.getCpf(), cliente);
        clientesPorEmail.put(cliente.getEmail(), cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso.");

        return cliente;
    }
}