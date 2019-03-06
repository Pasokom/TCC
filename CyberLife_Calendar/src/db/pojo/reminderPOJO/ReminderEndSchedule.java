package db.pojo.reminderPOJO;

import java.sql.Date;

public class ReminderEndSchedule {

    private int cod_fim_repeticao;
    private Date dia_fim;
    private int qtd_recorrencia;
    private int fk_lembrete;

    /**
     * @return the cod_fim_repeticao
     */
    public int getCod_fim_repeticao() {
        return cod_fim_repeticao;
    }

    /**
     * @param cod_fim_repeticao the cod_fim_repeticao to set
     */
    public void setCod_fim_repeticao(int cod_fim_repeticao) {
        this.cod_fim_repeticao = cod_fim_repeticao;
    }

    /**
     * @return the dia_fim
     */
    public Date getDia_fim() {
        return dia_fim;
    }

    /**
     * @param dia_fim the dia_fim to set
     */
    public void setDia_fim(Date dia_fim) {
        this.dia_fim = dia_fim;
    }

    /**
     * @return the qtd_recorrencia
     */
    public int getQtd_recorrencia() {
        return qtd_recorrencia;
    }

    /**
     * @param qtd_recorrencia the qtd_recorrencia to set
     */
    public void setQtd_recorrencia(int qtd_recorrencia) {
        this.qtd_recorrencia = qtd_recorrencia;
    }

    /**
     * @return the fk_lembrete
     */
    public int getFk_lembrete() {
        return fk_lembrete;
    }

    /**
     * @param fk_lembrete the fk_lembrete to set
     */
    public void setFk_lembrete(int fk_lembrete) {
        this.fk_lembrete = fk_lembrete;
    }
}