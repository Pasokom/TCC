package db.pojo.projectPOJO;

import java.sql.Timestamp;

import db.pojo.AppointmentDB;

public class TarefaDB extends AppointmentDB {

    private int cod_tarefa;
    private String nome_tarefa;
    private int fk_projeto;
    
    private Timestamp data_inicio;
    private Timestamp data_fim;

    public TarefaDB() {
        super("task");
    }

    /**
     * @return the cod_tarefa
     */
    public int getCod_tarefa() {
        return cod_tarefa;
    }

    /**
     * @param cod_tarefa the cod_tarefa to set
     */
    public void setCod_tarefa(int cod_tarefa) {
        this.cod_tarefa = cod_tarefa;
    }

    /**
     * @return the nome_tarefa
     */
    public String getNome_tarefa() {
        return nome_tarefa;
    }

    /**
     * @param nome_tarefa the nome_tarefa to set
     */
    public void setNome_tarefa(String nome_tarefa) {
        this.nome_tarefa = nome_tarefa;
    }

    /**
     * @return the fk_tarefa
     */
    public int getFk_projeto() {
        return fk_projeto;
    }

    /**
     * @param fk_tarefa the fk_tarefa to set
     */
    public void setFk_projeto(int fk_projeto) {
        this.fk_projeto = fk_projeto;
    }

    /**
     * @return the data_inicio
     */
    public Timestamp getData_inicio() {
        return data_inicio;
    }

    /**
     * @param data_inicio the data_inicio to set
     */
    public void setData_inicio(Timestamp data_inicio) {
        this.data_inicio = data_inicio;
    }

    /**
     * @return the data_fim
     */
    public Timestamp getData_fim() {
        return data_fim;
    }

    /**
     * @param data_fim the data_fim to set
     */
    public void setData_fim(Timestamp data_fim) {
        this.data_fim = data_fim;
    }

}