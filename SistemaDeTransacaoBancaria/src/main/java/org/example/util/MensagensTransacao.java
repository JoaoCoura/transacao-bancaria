package org.example.util;

public class MensagensTransacao {
    public static final String MENSAGEM_ERRO_DEPOSITAR_CONTA_INATIVA = "Não é possível depositar em uma conta inativa.";
    public static final String MENSAGEM_ERRO_SACAR_CONTA_INATIVA = "Não é possível sacar de uma conta inativa.";
    public static final String MENSAGEM_ERRO_CONTA_INATIVA_ORIGEM = "Não é possível transferir de uma conta inativa.";
    public static final String MENSAGEM_ERRO_CONTA_INATIVA_DESTINO = "Não é possível transferir para uma conta inativa.";
    public static final String MENSAGEM_ERRO_VALOR_NEGATIVO = "Valor da transação deve ser positivo.";
    public static final String MENSAGEM_ERRO_CASAS_DECIMAIS = "Valor da transação deve ter no máximo 2 casas decimais.";
    public static final String MENSAGEM_ERRO_SALDO_INSUFICIENTE = "Saldo insuficiente. Saldo Atual R$%.2f."; // quando usar moeda, moeda como variavel
    public static final String MENSAGEM_ERRO_PROPRIA_CONTA = "Não é possível transferir para a mesma conta.";
    public static final String MENSAGEM_ERRO_LIMITE = "Limite insuficiente. Limite Atual R$%.2f.";
    public static final String MENSAGEM_SUCESSO = "%s de R$%.2f realizado com sucesso. Saldo atual: R$%.2f.";

    private MensagensTransacao(){}
}
