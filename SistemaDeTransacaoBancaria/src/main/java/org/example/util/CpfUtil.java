package org.example.util;
import java.util.Random;

public class CpfUtil {
    public static String gerarCpfValido() {
        StringBuilder cpf = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            cpf.append(random.nextInt(10));
        }
        return cpf.toString();
    }
}
