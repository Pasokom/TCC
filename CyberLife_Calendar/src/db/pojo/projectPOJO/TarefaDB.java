package db.pojo.projectPOJO;

import java.sql.Date;
import java.sql.Timestamp;

import db.pojo.AppointmentDB;

public class TarefaDB extends AppointmentDB {

    private int cod_tarefa;
    private String nome_tarefa;
    private int duracao;
    private int importancia;
    private int dependencia;
    private boolean concluido;
    private Date data_concluido;
    private String fk_nome_marcador;
    private int fk_projeto;
    private int fk_usuario;
    
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

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getImportancia() {
        return importancia;
    }

    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }

    public String getFk_nome_marcador() {
        return fk_nome_marcador;
    }

    public void setFk_nome_marcador(String fk_nome_marcador) {
        this.fk_nome_marcador = fk_nome_marcador;
    }

    public int getDependencia() {
        return dependencia;
    }

    public void setDependencia(int dependencia) {
        this.dependencia = dependencia;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    @Override
    public String toString() {
        return this.nome_tarefa;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public Date getData_concluido() {
        return data_concluido;
    }

    public void setData_concluido(Date data_concluido) {
        this.data_concluido = data_concluido;
    }
}