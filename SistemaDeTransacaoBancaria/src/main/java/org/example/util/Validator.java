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

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException(nomeDoCampo + " deve conter exatamente 11 dígitos numéricos.");
        }
    }
}
