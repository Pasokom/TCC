package db.pojo.goalPOJO;

public class GoalDB {

    private int cod_meta;
    private String titulo;
    private int qtd_semana;
    private int duracao_minutos;
    private int periodo;
    private int fk_usuario;
    private int cod_recorrencia;

    private int cod_semana;
    private int qtd_concluido;

    /**
     * @return the cod_meta
     */
    public int getCod_meta() {
        return cod_meta;
    }

    /**
     * @param cod_meta the cod_meta to set
     */
    public void setCod_meta(int cod_meta) {
        this.cod_meta = cod_meta;
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
     * @return the qtd_semana
     */
    public int getQtd_semana() {
        return qtd_semana;
    }

    /**
     * @param qtd_semana the qtd_semana to set
     */
    public void setQtd_semana(int qtd_semana) {
        this.qtd_semana = qtd_semana;
    }

    /**
     * @return the duracao_minutos
     */
    public int getDuracao_minutos() {
        return duracao_minutos;
    }

    /**
     * @param duracao_minutos the duracao_minutos to set
     */
    public void setDuracao_minutos(int duracao_minutos) {
        this.duracao_minutos = duracao_minutos;
    }

    /**
     * @return the periodo
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the fk_usuario
     */
    public int getFk_usuario() {
        return fk_usuario;
    }

    /**
     * @param fk_usuario the fk_usuario to set
     */
    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
    }

    /**
     * @return the cod_recorrencia
     */
    public int getCod_recorrencia() {
        return cod_recorrencia;
    }

    /**
     * @param cod_recorrencia the cod_recorrencia to set
     */
    public void setCod_recorrencia(int cod_recorrencia) {
        this.cod_recorrencia = cod_recorrencia;
    }

    /**
     * @return the cod_semana
     */
    public int getCod_semana() {
        return cod_semana;
    }

    /**
     * @param cod_semana the cod_semana to set
     */
    public void setCod_semana(int cod_semana) {
        this.cod_semana = cod_semana;
    }

    /**
     * @return the qtd_concluido
     */
    public int getQtd_concluido() {
        return qtd_concluido;
    }

    /**
     * @param qtd_concluido the qtd_concluido to set
     */
    public void setQtd_concluido(int qtd_concluido) {
        this.qtd_concluido = qtd_concluido;
    }
}