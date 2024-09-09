package ada.tech.agenda.util;

import java.util.Scanner;

public class Util {

    public static void escrever(String mensagem) {
        System.out.println(mensagem);
    }

    public static void erro(String mensagem) {
        System.err.println(mensagem);
    }

    public static int ler(Scanner entrada, String questao) {
            System.out.println(questao);
            return entrada.nextInt();
    }

    public static String formatarTelefone(String telefone) {

        String telefoneLimpo = telefone.replaceAll("\\D", "");

        if (telefoneLimpo.length() != 11) {
            Util.erro("Telefone deve conter 11 dígitos.\n");
        }

        return telefoneLimpo.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
    }

}
