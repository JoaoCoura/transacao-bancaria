package org.example;

import org.example.modelo.CadastroCliente;
import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.example.modelo.TransacaoService;
import java.math.BigDecimal;


public class Main {
    public static void main(String[] args) {
        CadastroCliente cadastro = new CadastroCliente();
        Cliente cliente1 = cadastro.cadastrarCliente("Ocara", "12345678910", "ocara@gmail.com");
        Cliente cliente2 = cadastro.cadastrarCliente("Fuba", "78945612310", "fubs@gmail.com");

        Conta conta1 = new Conta(cliente1);
        Conta conta2 = new Conta(cliente2);

        System.out.println(conta1);
        System.out.println(conta2);

        TransacaoService gerenciador = new TransacaoService();

        gerenciador.depositar(conta1, new BigDecimal("500.00"));
        gerenciador.sacar(conta1, new BigDecimal("200.00"));
        gerenciador.transferir(conta1, conta2, new BigDecimal("100.00"));

        gerenciador.depositar(conta2, new BigDecimal("900.00"));
        


        // teste limite
        //gerenciador.transferir(conta1, conta2, new BigDecimal("300.00"));
        System.out.println(conta1.getSaldo());
        System.out.println(conta1.getLimite());
        System.out.println(conta1.getTipo());


        System.out.println(conta1);
        System.out.println(conta2);


    }

}