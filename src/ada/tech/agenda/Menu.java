package ada.tech.agenda;

import ada.tech.agenda.exception.ContatoNaoEncontradoException;
import ada.tech.agenda.exception.TelefoneExistenteException;
import ada.tech.agenda.model.*;
import ada.tech.agenda.util.SmsTwilio;
import ada.tech.agenda.util.Util;
import com.twilio.exception.ApiException;
import com.twilio.exception.AuthenticationException;
//import com.twilio.exception.ApiException;
//import com.twilio.exception.AuthenticationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Menu {

    private final Scanner entrada;
    private Agenda agenda;

    public Menu() {
        this.entrada = new Scanner(System.in);
        agenda = new Agenda();
    }

    public void iniciar() throws ContatoNaoEncontradoException {

        int opcao = 0;

        do {
            System.out.println();
            agenda.exibirAgendaCompleta();

            String opcoes = """
                    
                    = ------------------------------- =
                    |             𝗔𝗚𝗘𝗡𝗗𝗔              |
                    = ------------------------------- =
                    
                    = ---------=== Menu ===---------- =
                    | 1 - Adicionar Contato           |
                    | 2 - Detalhar Contato            |
                    | 3 - Editar Contato              |
                    | 4 - Remover Contato             |
                    | 5 - Enviar SMS                  |
                    | 6 - Buscar Contato              |
                    | 7 - Sair                        |
                    = ------------------------------- =
                    """;

            Util.escrever(opcoes);
            System.out.print("Digite uma opção: ");
            String opcaoString = entrada.next();

            try {
                opcao = Integer.parseInt(opcaoString);
                entrada.nextLine();
            } catch (NumberFormatException e) {
                System.err.println("ERRO! Informe uma opção válida!\n");
                continue;
            }

            // Mensagem de erro para opções inválidas
            if (opcao < 1 || opcao > 7) {
                Util.erro("ERRO! Informe uma opção válida!");
            }

            System.out.println();

            switch (opcao) {
                case 1:
                    menuAdicionarContato();
                    break;

                case 2:
                    menuDetalharContato();
                    break;

                case 3:
                    MenuEditar.menuEditarContato();
                    break;

                case 4:
                    menuRemoverContato();
                    break;

                case 5:
                    menuEnviarSms();
                    break;

                case 6:
                    menuBuscarContatoPorNome();
                    break;

                case 7:
                    System.out.println("Saindo...");
                    break;

                default:
                    break;
            }

        } while (opcao != 7);
    }

    public void menuAdicionarContato() {
        int opcaoInt = 0;

        do {
            String opcoesContato = """
                    
                    =-------------------------------- =
                    |        ADICIONAR CONTATO        |
                    = ------------------------------- =
                    
                    = ----=== Tipo de Contato ===---- =
                    | 1 - Contato empresa             |
                    | 2 - Contato pessoal             |
                    | 3 - Contato profissional        |
                    | 4 - Voltar                      |
                    = ------------------------------- =
                    """;

            Util.escrever(opcoesContato);
            System.out.print("Escolha o tipo de contato: ");
            String opcaoString = entrada.next();


            try {
                opcaoInt = Integer.parseInt(opcaoString);
                entrada.nextLine();
            } catch (NumberFormatException e) {
                Util.erro("ERRO! Informe uma opção válida (1, 2, 3 ou 4).\n");
                continue;
            }

            if (opcaoInt < 1 || opcaoInt > 4) {
                Util.erro("ERRO! Informe uma opção válida (1, 2, 3 ou 4).\n");
            }

            switch (opcaoInt) {
                case 1:
                    menuAdicionarContatoEmpresa();
                    break;
                case 2:
                    menuAdicionarContatoPessoal();
                    break;
                case 3:
                    menuAdicionarContatoProfissional();
                    break;
                case 4:
                    System.out.println("\nVoltando...");
                default:
                    break;
            }
        } while (opcaoInt != 4);
    }

    public void menuRemoverContato() {
        System.out.println("= --------------------------------- =");
        System.out.println("|         EXCLUIR CONTATO           |");
        System.out.println("= --------------------------------- =");
        System.out.print("\nInforme um número de tefone: ");
        String numeroTelefone = entrada.nextLine();

        String numeroTelefoneLimpo = numeroTelefone.replaceAll("[^0-9]", "");

        try {
            agenda.excluirContato(numeroTelefoneLimpo);
            System.out.println("\nCONTATO EXCLUIDO COM SUCESSO!");
        } catch (ContatoNaoEncontradoException e) {
            Util.erro("ERRO! Contato não encontrado.\n");
        }
    }


    public void menuEditarContato() throws ContatoNaoEncontradoException {
        MenuEditar.menuEditarContato();

    }

    public void menuDetalharContato() {
        System.out.print("Qual contato você deseja detalhar: ");
        String telefone = entrada.nextLine();

        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");

        try {
            agenda.detalharContato(telefoneLimpo);
        } catch (ContatoNaoEncontradoException e) {
            Util.erro("ERRO! Contato não encontrado.\n");
        }
    }

    public void menuEnviarSms(){
        System.out.println("= ------------------------------- =");
        System.out.println("|               SMS               |");
        System.out.println("= ------------------------------- =");

        System.out.print("Qual contato você deseja enviar SMS: ");
        String telefone = entrada.nextLine();
        try {
            Contato contato = agenda.buscarContatoPorTelefone(telefone);
            System.out.print("\nDigite a mensagem: ");
            String texto = entrada.nextLine();
            SmsTwilio sms = new SmsTwilio();
            Mensagem mensagem= new Mensagem(contato.getTelefone(),texto);
            sms.enviarSms(mensagem);
            System.out.println("Mensagem enviada para " + contato.getNome() + " com sucesso!");
            contato.adicionarMensagem(mensagem);
        } catch (ContatoNaoEncontradoException e) {
            Util.erro("Erro! Contato não encontrado.\n");
        }catch (ApiException e){
            Util.erro("Erro ao enviar.\n");
        }catch (AuthenticationException e){
            Util.erro("Erro ao autenticar ao Twilio, verifique as variáveis de ambiente.\n");
        }
    }

    public void menuBuscarContatoPorNome() {
        System.out.println("= ------------------------------- =");
        System.out.println("|          BUSCAR CONTATO         |");
        System.out.println("= ------------------------------- =");
        System.out.print("\nDigite o nome do contato: ");
        String nome = entrada.nextLine();

        try {
            agenda.buscarContatoPorNome(nome);
        } catch (ContatoNaoEncontradoException e) {
            Util.erro("ERRO! Contato não encontrado.");
        }
    }

    public static String subMenuEditarContato() {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println();
            System.out.println("= ------------------------------- =");
            System.out.println("|          EDITAR CONTATO         |");
            System.out.println("= ------------------------------- =");

            System.out.println("= ------------------------------- =");
            System.out.println("| 1 - Nome Completo               |");
            System.out.println("| 2 - Telefone                    |");
            System.out.println("| 3 - E-mail                      |");
            System.out.println("| 4 - Voltar ao menu principal    |");
            System.out.println("= ------------------------------- =");

            System.out.print("\nDigite a opção desejada: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    return "Nome";
                case 2:
                    return "Telefone";
                case 3:
                    return "Email";
                case 4:
                    return "";
                default:
                    Util.erro("Opção inválida");
            }
        } while (opcao != 4);

        return "";
    }

    private void menuAdicionarContatoEmpresa() {
        System.out.print("\nInforme o nome da empresa: ");
        String nomeEmpresa = entrada.nextLine();

        String telefoneEmpresa = obterTelefone();

        String emailEmpresa = obterEmail();

        String cnpjEmpresa = obterCnpj();

        System.out.print("\nInforme o logradouro da empresa: ");
        String logradouroEmpresa = entrada.nextLine();

        System.out.print("\nInforme o segmento da empresa: ");
        String segmentoEmpresa = entrada.nextLine();

        try {
            Contato contatoEmpresa = new ContatoEmpresa(
                    nomeEmpresa, telefoneEmpresa, emailEmpresa, 0, cnpjEmpresa, logradouroEmpresa, segmentoEmpresa);
            agenda.adicionarContato(contatoEmpresa);
            System.out.println("\nCONTATO EMPRESA ADICIONADO COM SUCESSO!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (TelefoneExistenteException e) {
            System.out.println("ERRO: Telefone já cadastrado.");
        }
    }

    private void menuAdicionarContatoProfissional() {
        System.out.print("\nInforme o primeiro nome: ");
        String primeiroNome = entrada.nextLine();

        System.out.print("\nInforme o sobrenome: ");
        String sobrenome = entrada.nextLine();

        String telefone = obterTelefone();

        String email = obterEmail();

        System.out.print("\nInforme o cargo: ");
        String cargo = entrada.nextLine();

        System.out.print("\nInforme a empresa: ");
        String empresa = entrada.nextLine();

        Contato contatoProfissional = new ContatoProfissional(
                primeiroNome, sobrenome, telefone, email, 0, cargo, empresa);

        try {
            agenda.adicionarContato(contatoProfissional);
            System.out.println("\nCONTATO PROFISSIONAL ADICIONADO COM SUCESSO!");
        } catch (TelefoneExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void menuAdicionarContatoPessoal() {
        System.out.print("\nInforme o primeiro nome: ");
        String primeiroNome = entrada.nextLine();

        System.out.print("\nInforme o sobrenome: ");
        String sobrenome = entrada.nextLine();

        String telefone = obterTelefone();

        String email = obterEmail();

        System.out.print("\nInforme o apelido: ");
        String apelido = entrada.nextLine();

        Relacao relacao = obterRelacao();

        System.out.print("\nInforme o aniversário (dd/MM/yyyy): ");
        LocalDate aniversario = obterAniversario();

        Contato contatoPessoal = new ContatoPessoal(
                primeiroNome, sobrenome, telefone, email, 0, apelido, relacao, aniversario);

        try {
            agenda.adicionarContato(contatoPessoal);
            System.out.println("\nCONTATO PESSOAL ADICIONADO!");
        } catch (TelefoneExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    private String obterTelefone() {
        while (true) {
            System.out.print("\nInforme o telefone: ");
            String telefone = entrada.nextLine();

            if (!telefone.matches("\\d+")) {
                Util.erro("ERRO! O telefone deve conter apenas números.\n");
                continue;
            }
            if (telefone.length() != 11) {
                Util.erro("ERRO! Telefone deve conter 11 dígitos.\n");
                continue;
            }

            return telefone;
        }
    }

    private String obterEmail() {
        while (true) {
            System.out.print("\nInforme o e-mail: ");
            String email = entrada.nextLine();
            if (email.contains("@")) {
                return email;
            } else {
                System.out.println("\nERRO! O e-mail deve conter '@' e ter um formato válido.");
            }
        }
    }

    public Relacao obterRelacao() {
        System.out.println("\n= -------==== Relação ===------- =");
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

    public LocalDate obterAniversario() {
        LocalDate aniversario = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    private String obterCnpj() {
        while (true) {
            System.out.print("\nInforme o CNPJ: ");
            String cnpj = entrada.nextLine();

            if (!cnpj.matches("\\d+")) {
                System.out.println("\nERRO! O CNPJ deve conter apenas números.");
                continue;
            }

            if (cnpj.length() != 14) {
                System.out.println("\nERRO! O CNPJ deve conter exatamente 14 dígitos.");
                continue;
            }

            return cnpj;
        }
    }





}