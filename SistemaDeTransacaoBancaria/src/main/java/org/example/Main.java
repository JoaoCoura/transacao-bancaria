package org.example;

import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.example.modelo.TransacaoService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Bia", "123456789", "bia@gmail.com");
        Cliente cliente2 = new Cliente("Fuba", "789456123", "fubs@gmail.com");

        Conta conta1 = new Conta(cliente1);
        Conta conta2 = new Conta(cliente2);

        System.out.println(conta1);
        System.out.println(conta2);

        TransacaoService gerenciador = new TransacaoService();

        gerenciador.depositar(conta1, new BigDecimal("500.00"));
        gerenciador.sacar(conta1, new BigDecimal("200.00"));
        gerenciador.transferir(conta1, conta2, new BigDecimal("100.00"));

        System.out.println(conta1);
        System.out.println(conta2);

        gerenciador.exibirHistorico();


    }

}