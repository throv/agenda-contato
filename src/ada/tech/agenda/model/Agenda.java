package ada.tech.agenda.model;

import ada.tech.agenda.Menu;
import ada.tech.agenda.exception.ContatoNaoEncontradoException;
import ada.tech.agenda.exception.TelefoneExistenteException;
import ada.tech.agenda.util.Persistencia;


import java.time.LocalDate;
import java.util.*;

public class Agenda {

    static List<Contato> listaContatos;

    public Agenda() {

        this.listaContatos =  Persistencia.lerArquivoAgenda();

    }

    public void buscarContatoPorNome(String nomeContato) throws ContatoNaoEncontradoException {

        List<Contato> contatoBuscado = new ArrayList<>();

        for(Contato contato : listaContatos) {
            if(contato.
                    getNome().
                    toLowerCase().
                    contains(nomeContato.toLowerCase()) || contato.
                    getSobreNome().
                    toLowerCase().
                    contains(nomeContato.toLowerCase())) {
                contatoBuscado.add(contato);
            }
        }

        if(contatoBuscado.isEmpty()) {
            throw new ContatoNaoEncontradoException();
        } else {

            for(Contato contato : contatoBuscado) {
                System.out.println(contato);
            }
        }

    }

    public void adicionarContato(Contato novoContato) throws TelefoneExistenteException {

        try {
            buscarContatoPorTelefone(novoContato.getTelefone());

            throw new TelefoneExistenteException();

        } catch (ContatoNaoEncontradoException e) {

            listaContatos.add(novoContato);
            definirID();

            Persistencia.gravarContatos(this.listaContatos);
        }
    }

    @Override
    public String toString() {
        return listaContatos.toString();
    }

    public void excluirContato(String telefone) throws ContatoNaoEncontradoException {

        Contato contato = buscarContatoPorTelefone(telefone);

        listaContatos.remove(contato);

        definirID();

        Persistencia.gravarContatos(this.listaContatos);

    }

    public void editarContato(String telefone) throws ContatoNaoEncontradoException {

        Contato contato = buscarContatoPorTelefone(telefone);

        String seletor = Menu.subMenuEditarContato();

        switch (seletor.toUpperCase()) {
            case "NOME":
                editarNome(contato);
                break;
            case "TELEFONE":
                editarTelefone(contato);
                break;
            case "EMAIL":
                editarEmail(contato);
                break;
            default:
                System.out.println("Opção inválida!");
        }

        Persistencia.gravarContatos(this.listaContatos);
    }

    public static void editarNome(Contato contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o primeiro nome: ");
        String novoNome = sc.next();
        contato.setNome(novoNome);
        System.out.print("\nInforme o seu sobrenome: ");
        String novoSobrenome = sc.next();
        contato.setSobreNome(novoSobrenome);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarTelefone(Contato contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo número: ");
        String novoNumero = sc.next();

        if (!novoNumero.matches("\\d+")) {
            System.out.println("ERRO! Número deve conter apenas dígitos.");

        }

        try {
            buscarContatoPorTelefone(novoNumero);
            System.out.println("ERRO! Número já está em uso!");
        } catch (ContatoNaoEncontradoException e) {
            contato.setTelefone(novoNumero);
            System.out.println("\nCONTATO EDITADO!");
        }
    }

    public static void editarEmail(Contato contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o seu e-mail: ");
        String novoEmail = sc.nextLine();

        if (!novoEmail.contains("@")) {
            System.out.println("ERRO! O e-mail deve conter '@' e ter um formato válido.");
        } else {
            contato.setEmail(novoEmail);
            System.out.println("\nCONTATO EDITADO!");
        }
    }

    public static void editarCNPJ(ContatoEmpresa contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o novo CPF: ");
        String novoCPF = sc.next();
        contato.setCnpj(novoCPF);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarEmpresa(ContatoProfissional contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo nome da empresa: ");
        String novoNome = sc.next();
        contato.setEmpresa(novoNome);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarLogradouro(ContatoEmpresa contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo endereço: ");
        String novoNome = sc.next();
        contato.setLogradouro(novoNome);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarSegmento(ContatoEmpresa contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo seguemento: ");
        String novoSegmento = sc.next();
        contato.setSegmento(novoSegmento);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarApelido(ContatoPessoal contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo apelido: ");
        String novoApelido = sc.next();
        contato.setApelido(novoApelido);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarRelacao(ContatoPessoal contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Infome a nova relação: ");
        String novoNome = sc.next();
        contato.setRelacao(Relacao.valueOf(novoNome));
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarAniversario(ContatoPessoal contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme a nova data de aniversario:  ");
        LocalDate novoAniversario = LocalDate.parse(sc.next());
        contato.setAniversario(novoAniversario);
        System.out.println("\nCONTATO EDITADO!");
    }

    public static void editarCargo(ContatoProfissional contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o novo cargo: " );
        String novoNome = sc.next();
        contato.setCargo(novoNome);
        System.out.println("CONTATO EDITADO! \n");
    }

    public void detalharContato(String telefone) throws ContatoNaoEncontradoException {
        Contato contato = buscarContatoPorTelefone(telefone);

        System.out.println(contato);
    }

    private void definirID() {
        for (int i = 0; i < listaContatos.size(); i++) {
            listaContatos.get(i).setID(i+1);
        }
    }

    public String formatarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return "";
        }
        return Character.toUpperCase(nome.charAt(0)) + nome.substring(1).toLowerCase();
    }

    private String formatarTelefone(String telefone) {
        if (telefone.length() == 11) {
            return String.format("(%s) %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 7),
                    telefone.substring(7));
        }

        return telefone;
    }

    public void exibirAgendaCompleta() {

        int tamanhoId = 3;
        int tamanhoNome = 15;
        int tamanhoTelefone = 18;
        int tamanhoEmail = 25;
        int tamanhoTipoContato = 20;


        System.out.println("=" + "-".repeat(tamanhoId + tamanhoNome + tamanhoTelefone + tamanhoEmail + tamanhoTipoContato + 14) + "=");
        System.out.printf("| %-" + tamanhoId + "s | %-" + tamanhoNome + "s | %-" + tamanhoTelefone + "s | %-" + tamanhoEmail + "s | %-" + tamanhoTipoContato + "s | %n",
                "ID", "Nome", "Telefone", "E-mail", "Tipo do Contato");
        System.out.println("=" + "-".repeat(tamanhoId + tamanhoNome + tamanhoTelefone + tamanhoEmail + tamanhoTipoContato + 14) + "=");

        for (Contato contato : listaContatos) {
            String tipoContato = contato.getClass().getSimpleName();
            System.out.printf("| %-" + tamanhoId + "d | %-" + tamanhoNome + "s | %-" + tamanhoTelefone + "s | %-" + tamanhoEmail + "s | %-" + tamanhoTipoContato + "s | %n",
                    contato.getID(),
                    formatarNome(contato.getNome()) + " " + formatarNome(contato.getSobreNome()),
                    formatarTelefone(contato.getTelefone()),
                    contato.getEmail(),
                    tipoContato);
            System.out.println("=" + "-".repeat(tamanhoId + tamanhoNome + tamanhoTelefone + tamanhoEmail + tamanhoTipoContato + 14) + "=");
        }
        System.out.println();
    }

    public static Contato buscarContatoPorTelefone(String telefone) throws ContatoNaoEncontradoException {

        for(Contato contato : listaContatos) {
            if(contato.getTelefone().equals(telefone)) {
                return contato;
            }
        }

        throw new ContatoNaoEncontradoException();


    }

}
