package ada.tech.agenda.model;

import ada.tech.agenda.Menu;
import ada.tech.agenda.exception.ContatoNaoEncontradoException;
import ada.tech.agenda.exception.TelefoneExistenteException;
import ada.tech.agenda.util.Persistencia;
import ada.tech.agenda.util.Util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.awt.SystemColor.MENU;
import static java.awt.SystemColor.menu;

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

        Persistencia.gravarContatos(listaContatos);
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
        Persistencia.gravarContatos(listaContatos);
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
        Persistencia.gravarContatos(listaContatos);
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
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarCNPJ(ContatoEmpresa contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o novo CNPJ: ");
        String novoCPF = sc.next();
        try {
            contato.setCnpj(novoCPF);
            System.out.println("\nCONTATO EDITADO!");
        }
        catch (IllegalArgumentException e ) {
            System.out.println("Numero de CNPJ invalido !");
        }
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarEmpresa(ContatoProfissional contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo nome da empresa: ");
        String novoNome = sc.next();
        contato.setEmpresa(novoNome);
        System.out.println("\nCONTATO EDITADO!");
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarLogradouro(ContatoEmpresa contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo endereço: ");
        String novoNome = sc.next();
        contato.setLogradouro(novoNome);
        System.out.println("\nCONTATO EDITADO!");
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarSegmento(ContatoEmpresa contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo seguemento: ");
        String novoSegmento = sc.next();
        contato.setSegmento(novoSegmento);
        System.out.println("\nCONTATO EDITADO!");
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarApelido(ContatoPessoal contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o novo apelido: ");
        String novoApelido = sc.next();
        contato.setApelido(novoApelido);
        System.out.println("\nCONTATO EDITADO!");
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarRelacao(ContatoPessoal contato) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        System.out.println("Relação atual: " + contato.getRelacao());
        Relacao novaRelacao = obterRelacaoAgenda();
        contato.setRelacao(novaRelacao);
        System.out.println("\nCONTATO EDITADO!");
        //System.out.println("Nova relação: " + contato.getRelacao());
        Persistencia.gravarContatos(listaContatos);
    }

    public static void editarAniversario(ContatoPessoal contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o aniversário (dd/MM/yyyy): ");
        LocalDate aniversario = obterAniversarioAgenda();
        contato.setAniversario(aniversario);
        System.out.println("\nCONTATO EDITADO!");
        Persistencia.gravarContatos(listaContatos);
    }

    // Remover obterAniversioAgenda e obterRelacaoAgenda <- Original deve ficar em MENU.CLASS
    
    public static LocalDate obterAniversarioAgenda() {
        LocalDate aniversario = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner entrada = new Scanner(System.in);
        while (aniversario == null) {
            try {
                String aniversarioStr = entrada.nextLine();
                aniversario = LocalDate.parse(aniversarioStr, formatter);
            } catch (Exception e) {
                Util.erro("\nFormato de data inválido. Tente novamente.");
            }
        }

        return aniversario;
    }

    public static Relacao obterRelacaoAgenda() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("= -------==== Relação ===------- =");
        for (Relacao r : Relacao.values()) {
            System.out.println("| " + r.ordinal() + " - " + r.name());
        }
        System.out.println("= ------------------------------ =");

        Relacao relacao = null;
        while (relacao == null) {
            try {
                System.out.print("\nEscolha a relação: ");
                int opcaoRelacao = Integer.parseInt(entrada.nextLine());
                relacao = Relacao.values()[opcaoRelacao];
            } catch (Exception e) {
                Util.erro("\nOpção inválida. Escolha um número correspondente.");
            }
        }

        return relacao;
    }


    public static void editarCargo(ContatoProfissional contato) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o novo cargo: " );
        String novoNome = sc.next();
        contato.setCargo(novoNome);
        System.out.println("CONTATO EDITADO! \n");
        Persistencia.gravarContatos(listaContatos);
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

        System.out.println("=" + "-".repeat(tamanhoId + tamanhoNome + tamanhoTelefone + tamanhoEmail + 11) + "=");
        System.out.printf("| %-" + tamanhoId + "s | %-" + tamanhoNome + "s | %-" + tamanhoTelefone + "s | %-" + tamanhoEmail + "s |%n",
                "ID", "Nome", "Telefone", "E-mail");
        System.out.println("=" + "-".repeat(tamanhoId + tamanhoNome + tamanhoTelefone + tamanhoEmail + 11) + "=");

        for (Contato contato : listaContatos) {

            System.out.printf("| %-" + tamanhoId + "d | %-" + tamanhoNome + "s | %-" + tamanhoTelefone + "s | %-" + tamanhoEmail + "s |%n",
                    contato.getID(),
                    formatarNome(contato.getNome()) + " " + formatarNome(contato.getSobreNome()),
                    formatarTelefone(contato.getTelefone()),
                    contato.getEmail()
            );
            System.out.println("=" + "-".repeat(tamanhoId + tamanhoNome + tamanhoTelefone + tamanhoEmail + 11) + "=");
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
