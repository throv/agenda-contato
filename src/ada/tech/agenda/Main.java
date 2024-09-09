package ada.tech.agenda;

import ada.tech.agenda.exception.ContatoNaoEncontradoException;

public class Main {
    public static void main(String[] args) throws ContatoNaoEncontradoException {
        Menu menu = new Menu();
        menu.iniciar();
    }
}