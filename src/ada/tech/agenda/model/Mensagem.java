package ada.tech.agenda.model;



public class Mensagem {
    private String telefone;
    private String texto;



    public Mensagem(String telefone, String texto){
        this.telefone = telefone;
        this.texto = texto;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }




}
