package ada.tech.agenda.model;

import java.util.ArrayList;
import java.util.Objects;

public class Contato {

    private String nome;
    private String sobreNome;
    private String telefone;
    private String email;
    private int ID;
    private ArrayList<Mensagem> mensagens;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(telefone, contato.telefone);
    }

    public Contato(String nome, String sobreNome, String telefone, String email, int ID) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefone = telefone;
        this.email = email;
        this.ID = ID;
        this.mensagens = new ArrayList<>();
    }

    @Override
    public String toString() {
        String dados = String.format("""

                = --------=== DADOS DO CONTATO ===--------
                | ID: %s
                | Nome: %s %s
                | Telefone: %s
                | E-mail: %s
                = ----------------------------------------
                """, ID, nome, sobreNome, telefone, email);
        String mensagens = "| ====== MENSAGENS =======\n";
        if (this.mensagens != null && !this.mensagens.isEmpty()) {
            for (Mensagem mensagem : this.mensagens) {
                mensagens += mensagem.getTexto() + "\n";
            }
        }else{
            mensagens+= "Este contato não possui mensagens\n";
        }
        return  dados + mensagens + "\\ ==============================";

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void adicionarMensagem(Mensagem mensagem) {
        if (mensagens == null) {
            mensagens = new ArrayList<>();
        }
        mensagens.add(mensagem);

    }
}