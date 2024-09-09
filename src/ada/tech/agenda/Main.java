package ada.tech.agenda;

import ada.tech.agenda.exception.ContatoNaoEncontradoException;
import ada.tech.agenda.model.Login;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ContatoNaoEncontradoException, IOException {
        Menu menu = new Menu();
        Login login = new Login();
        login.verificarExistenciaLogin();
    }
}