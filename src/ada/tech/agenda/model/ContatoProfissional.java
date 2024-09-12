package ada.tech.agenda.model;

import ada.tech.agenda.util.Util;

public class ContatoProfissional extends Contato {

    private String cargo;
    private String empresa;

    private String tipo = "ContatoProfissional";

    public ContatoProfissional(String nome, String sobreNome, String telefone, String email, int ID, String cargo, String empresa) {
        super(nome, sobreNome, telefone, email, ID);
        this.cargo = cargo;
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        String dados= String.format("""
            
            = ----=== DADOS DO CONTATO PROFISSIONAL ===----
            | Nome: %s %s
            | Telefone: %s
            | E-mail: %s
            | Cargo: %s
            | Empresa: %s
            = ---------------------------------------------
            """, getNome(), getSobreNome(), Util.formatarTelefone(getTelefone()), getEmail(), cargo, empresa);
        String mensagens = "\n= ---------=== MENSAGENS ===----------- =\n";
        if (getMensagens() != null && !getMensagens().isEmpty()) {
            for (Mensagem mensagem : getMensagens()) {
                mensagens += mensagem.getTexto() + "\n";
            }
        }else{
            mensagens+= "    Este contato não possui mensagens\n";
        }
        return  dados + mensagens + "= ------------------------------------- =";


    }
}