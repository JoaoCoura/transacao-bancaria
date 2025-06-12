package org.example;

import org.example.modelo.CadastroCliente;
import org.example.modelo.Cliente;
import org.example.modelo.Conta;
import org.example.modelo.TransacaoService;
import java.math.BigDecimal;
import java.sql.SQLOutput;


public class Main {
    public static void main(String[] args) {
        CadastroCliente cadastro = new CadastroCliente();
        Cliente cliente1 = cadastro.cadastrarCliente("Ocara", "52527579841", "ocara@gmail.com");
        Cliente cliente2 = cadastro.cadastrarCliente("Fuba", "233.133.978-32", "fubs@gmail.com");
        System.out.println();

        Conta conta1 = new Conta(cliente1);
        Conta conta2 = new Conta(cliente2);
        System.out.println(conta1);
        System.out.println(conta2);
        System.out.println();

        TransacaoService gerenciador = new TransacaoService();
        gerenciador.depositar(conta1, new BigDecimal("500.00"));
        System.out.println();

        System.out.println("Historico de Transacoes de Ocara: ");
        conta1.imprimirHistoricoTransacoes();
        System.out.println();

        System.out.println(conta1);
        System.out.println();

        //conta1.adicionarContato(conta2); //
        conta1.mostrarContatos();
        System.out.println();

        gerenciador.transferir(conta1, conta2, new BigDecimal("900.00"));
        System.out.println("Historico de Transacoes de Ocara: ");
        conta1.imprimirHistoricoTransacoes();
        System.out.println();

        System.out.println("Historico de Transacoes de Fuba: ");
        conta2.imprimirHistoricoTransacoes();
        System.out.println();

        System.out.println(conta1);
        System.out.println(conta2);
        System.out.println();

        gerenciador.depositar(conta1, new BigDecimal("1000.00"));
        System.out.println(conta1);

        conta1.removerContato(conta2);
        gerenciador.transferir(conta1, conta2, new BigDecimal("1000.00")); //
        //gerenciador.transferir(conta1, conta2, new BigDecimal("900.00")); //
        System.out.println();

        System.out.println(conta1);
        gerenciador.depositar(conta1, new BigDecimal("900.00"));

        System.out.println(conta1);
        System.out.println("Historico de Transacoes de Ocara: ");
        conta1.imprimirHistoricoTransacoes();
        System.out.println();

        System.out.println(conta2);
        System.out.println("Historico de Transacoes de Fuba: ");
        conta2.imprimirHistoricoTransacoes();
    }
}