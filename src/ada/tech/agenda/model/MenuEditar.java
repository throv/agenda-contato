package ada.tech.agenda.model;

import ada.tech.agenda.exception.ContatoNaoEncontradoException;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuEditar {

    public static void menuEditarContato() throws ContatoNaoEncontradoException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o telefone que deseja editar: ");
        String buscarTelefone = sc.nextLine();
        Contato contato = Agenda.buscarContatoPorTelefone(buscarTelefone);

        if (contato != null) {
            if (contato instanceof ContatoEmpresa) {
                exibirMenuContatoEmpresa((ContatoEmpresa) contato);
            } else if (contato instanceof ContatoPessoal) {
                exibirMenuContatoPessoal((ContatoPessoal) contato);
            } else if (contato instanceof ContatoProfissional) {
                exibirMenuContatoProfissional((ContatoProfissional) contato);
            }
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    public static void exibirMenuContatoEmpresa(ContatoEmpresa contato) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Editar Contato Empresa");
        System.out.println("1. Alterar nome");
        System.out.println("2. Alterar telefone");
        System.out.println("3. Alterar email");
        System.out.println("4. Alterar CNPJ");
        System.out.println("5. Alterar logradouro");
        System.out.println("6. Alterar segmento");

        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consumir nova linha

        switch (opcao) {
            case 1:
                Agenda.editarNome(contato);
                break;
            case 2:
                Agenda.editarTelefone(contato);
                break;
            case 3:
                Agenda.editarEmail(contato);
                break;
            case 4:
                Agenda.editarCNPJ(contato);
                break;
            case 5:
                Agenda.editarLogradouro(contato);
                break;
            case 6:
                Agenda.editarSegmento(contato);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    public static void exibirMenuContatoPessoal(ContatoPessoal contato) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("EDITR CONTATO PESSOAL");
        System.out.println("1. Alterar nome");
        System.out.println("2. Alterar telefone");
        System.out.println("3. Alterar email");
        System.out.println("4. Alterar apelido");
        System.out.println("5. Alterar relação");
        System.out.println("6. Alterar aniversário");

        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consumir nova linha

        switch (opcao) {
            case 1:
                Agenda.editarNome(contato);
                break;
            case 2:
                Agenda.editarTelefone(contato);
                break;
            case 3:
                Agenda.editarEmail(contato);;
                break;
            case 4:
                Agenda.editarApelido(contato);
                break;
            case 5:
                Agenda.editarRelacao(contato);
                break;
            case 6:
                Agenda.editarAniversario(contato);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    public static void exibirMenuContatoProfissional(ContatoProfissional contato) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Editar Contato Profissional");
        System.out.println("1. Alterar nome");;
        System.out.println("2. Alterar telefone");
        System.out.println("3. Alterar email");
        System.out.println("4. Alterar cargo");
        System.out.println("5. Alterar empresa");

        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consumir nova linha

        switch (opcao) {
            case 1:
                Agenda.editarNome(contato);
                break;
            case 2:
                Agenda.editarTelefone(contato);
                break;
            case 3:
                Agenda.editarEmail(contato);
                break;
            case 4:
                Agenda.editarCargo(contato);
                break;
            case 5:
                Agenda.editarEmpresa(contato);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
}
