package org.example.util;

import java.util.regex.Pattern;

public class Validator {
    public static void verificarNaoNulo(Object valor, String nomeDoCampo)
    {
        if (valor == null) {
            throw new IllegalArgumentException(nomeDoCampo + " não pode ser nulo.");
        }
    }

    public static void verificarEmailValido(String email, String nomeDoCampo)
    {
        verificarNaoNulo(email, nomeDoCampo);

        if (email.length() > 257) {
            throw new IllegalArgumentException(nomeDoCampo + " deve ter no máximo 257 caracteres.");
        }

        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        boolean valido = Pattern.matches(regex, email);

        if (!valido) {
            throw new IllegalArgumentException(nomeDoCampo + " está em formato inválido.");
        }
    }

    public static void verificarCpfValido(String cpf, String nomeDoCampo)
    {
        verificarNaoNulo(cpf, nomeDoCampo);

        cpf = cpf.replaceAll("[^\\d]", "");

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException(nomeDoCampo + " deve conter exatamente 11 dígitos numéricos.");
        }

        if (cpf.matches("(\\d)\\1{10}"))
        {
            throw new IllegalArgumentException(nomeDoCampo + " não deve ter uma sequência de números iguais.");
        }

        int soma = 0;
        for (int i = 0; i < 9; i++)
            soma += (cpf.charAt(i) - '0') * (10 - i);
        int dig1 = 11 - (soma % 11);
        dig1 = (dig1 >= 10) ? 0 : dig1;

        soma = 0;
        for (int i = 0; i < 10; i++)
            soma += (cpf.charAt(i) - '0') * (11 - i);
        int dig2 = 11 - (soma % 11);
        dig2 = (dig2 >= 10) ? 0 : dig2;
        if (!(dig1 == (cpf.charAt(9) - '0') && dig2 == (cpf.charAt(10) - '0'))) {
            throw new IllegalArgumentException(nomeDoCampo + " não deve conter dígitos verificadores incorretos.");
        }
    }
}
