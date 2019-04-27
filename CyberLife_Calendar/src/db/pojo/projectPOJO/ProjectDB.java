package db.pojo.projectPOJO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProjectDB {

    private int cod_projeto;
    private String titulo;
    private Timestamp data_entrega;
    private ArrayList<TarefaDB> tarefas = new ArrayList<>();

    /**
     * @return the cod_projeto
     */
    public int getCod_projeto() {
        return cod_projeto;
    }

    /**
     * @param cod_projeto the cod_projeto to set
     */
    public void setCod_projeto(int cod_projeto) {
        this.cod_projeto = cod_projeto;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the data_entrega
     */
    public Timestamp getData_entrega() {
        return data_entrega;
    }

    /**
     * @param data_entrega the data_entrega to set
     */
    public void setData_entrega(Timestamp data_entrega) {
        this.data_entrega = data_entrega;
    }

    /**
     * @return the tarefas
     */
    public ArrayList<TarefaDB> getTarefas() {
        return tarefas;
    }

    /**
     * @param tarefas the tarefas to set
     */
    public void setTarefas(ArrayList<TarefaDB> tarefas) {
        this.tarefas = tarefas;
    }
}