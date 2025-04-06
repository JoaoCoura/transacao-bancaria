package org.example.util;

import java.util.regex.Pattern;

public class Validator {
        public static void requerNaoNulo(Object valor, String nomeDoCampo)
        {
            if (valor == null) {
                throw new IllegalArgumentException(nomeDoCampo + " não pode ser nulo.");
            }
        }

    public static void requerEmailValido(String email, String campo)
    {
        requerNaoNulo(email, campo);

        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        boolean valido = Pattern.matches(regex, email);

        if (!valido) {
            throw new IllegalArgumentException(campo + " está em formato inválido.");
        }
    }

    public static void requerCpfValido(String cpf, String campo)
    {
        requerNaoNulo(cpf, campo);

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException(campo + " deve conter exatamente 11 dígitos numéricos.");
        }
    }
}
