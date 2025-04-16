package org.example.modelo;

import java.math.BigDecimal;

public class TransacaoService {
    public static final String MENSAGEM_ERRO_DEPOSITAR_CONTA_INATIVA = "Não é possível depositar em uma conta inativa.";
    public static final String MENSAGEM_ERRO_SACAR_CONTA_INATIVA = "Não é possível sacar de uma conta inativa.";
    public static final String MENSAGEM_ERRO_CONTA_INATIVA_ORIGEM = "Não é possível transferir de uma conta inativa.";
    public static final String MENSAGEM_ERRO_CONTA_INATIVA_DESTINO = "Não é possível transferir para uma conta inativa.";
    public static final String MENSAGEM_ERRO_VALOR_NEGATIVO = "Valor d%s %s deve ser positivo.";
    public static final String MENSAGEM_ERRO_CASAS_DECIMAIS = "Valor d%s %s deve ter no máximo 2 casas decimais.";
    public static final String MENSAGEM_ERRO_SALDO_INSUFICIENTE = "Saldo insuficiente. Saldo Atual R$%.2f."; // quando usar moeda, moeda como variavel
    public static final String MENSAGEM_ERRO_PROPRIA_CONTA = "Não é possível transferir para a mesma conta.";
    public static final String MENSAGEM_ERRO_LIMITE = "Limite insuficiente. Limite Atual R$%.2f.";
    public static final String MENSAGEM_SUCESSO = "%s de R$%.2f realizado com sucesso. Saldo atual: R$%.2f.";

    public void depositar(Conta destino, BigDecimal valor) {

        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_DEPOSITAR_CONTA_INATIVA);
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_VALOR_NEGATIVO, "o", "depósito"));
        }

        if (valor.scale() > 2) {
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_CASAS_DECIMAIS, "o", "depósito"));
        }

        destino.adicionarSaldo(valor);
        System.out.printf((MENSAGEM_SUCESSO) + "%n", "Depósito", valor.doubleValue(), destino.getSaldo().doubleValue());
    }

    public void sacar(Conta origem, BigDecimal valor) {
        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_SACAR_CONTA_INATIVA);
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_VALOR_NEGATIVO, "o", "saque"));
        }

        if (valor.scale() > 2) {
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_CASAS_DECIMAIS, "o", "saque"));
        }

        if(origem.getSaldo().compareTo(valor) < 0){
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, origem.getSaldo().doubleValue()));
        }

        origem.subtrairSaldo(valor);
        System.out.printf((MENSAGEM_SUCESSO) + "%n", "Saque", valor.doubleValue(), origem.getSaldo().doubleValue());
    }

    public void transferir(Conta origem, Conta destino, BigDecimal valor) {

        if (destino.getNumeroConta() == origem.getNumeroConta()) {
            throw new IllegalArgumentException(MENSAGEM_ERRO_PROPRIA_CONTA);
        }

        if (origem.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_CONTA_INATIVA_ORIGEM);
        }

        if(destino.getStatus() == TipoStatus.INATIVA){
            throw new IllegalArgumentException(MENSAGEM_ERRO_CONTA_INATIVA_DESTINO);
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_VALOR_NEGATIVO, "a", "transferência"));
        }

        if (valor.scale() > 2) {
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_CASAS_DECIMAIS, "a", "transferência"));
        }

        if (origem.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_SALDO_INSUFICIENTE, origem.getSaldo().doubleValue()));
        }

        if(origem.getLimite().compareTo(valor) == -1){
            throw new IllegalArgumentException(String.format(MENSAGEM_ERRO_LIMITE, origem.getLimite().doubleValue()));
        }

        origem.subtrairSaldo(valor);
        destino.adicionarSaldo(valor);
        System.out.println("Transferência de R$" + valor + " de "+ origem.getTitular().getNome() + " para " +
                destino.getTitular().getNome() + " realizada com sucesso!");
    }
}

