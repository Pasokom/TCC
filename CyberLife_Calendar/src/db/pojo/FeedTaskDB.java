package db.pojo;

import java.sql.Timestamp;

public class FeedTaskDB {

    private int fk_usuario;
    private String usuario_nome;
    private String tarefa_nome;
    private Timestamp tarefa_data;

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getTarefa_nome() {
        return tarefa_nome;
    }

    public void setTarefa_nome(String tarefa_nome) {
        this.tarefa_nome = tarefa_nome;
    }

    public Timestamp getTarefa_data() {
        return tarefa_data;
    }

    public void setTarefa_data(Timestamp tarefa_data) {
        this.tarefa_data = tarefa_data;
    }
}