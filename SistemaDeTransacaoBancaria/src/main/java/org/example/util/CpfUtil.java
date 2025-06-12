package org.example.util;
import java.util.Random;

public class CpfUtil {
    public static String gerarCpfValido() {

        Random rand = new Random();

        int[] numeros = new int[11];
        for (int i = 0; i < 9; i++) {
            numeros[i] = rand.nextInt(10);
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += numeros[i] * (10 - i);
        }
        int dig1 = 11 - (soma % 11);
        numeros[9] = (dig1 >= 10) ? 0 : dig1;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += numeros[i] * (11 - i);
        }
        int dig2 = 11 - (soma % 11);
        numeros[10] = (dig2 >= 10) ? 0 : dig2;

        StringBuilder cpf = new StringBuilder();
        for (int num : numeros) {
            cpf.append(num);
        }

        return cpf.toString();
    }
}
