package db.pojo;

public class LabelDB {

    private String nome_marcador;
    private int fk_projeto;

    public String getNome_marcador() {
        return nome_marcador;
    }

    public void setNome_marcador(String nome_marcador) {
        this.nome_marcador = nome_marcador;
    }

    public int getFk_projeto() {
        return fk_projeto;
    }

    public void setFk_projeto(int fk_projeto) {
        this.fk_projeto = fk_projeto;
    }
}