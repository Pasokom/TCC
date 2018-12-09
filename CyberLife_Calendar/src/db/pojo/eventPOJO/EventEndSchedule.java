package db.pojo.eventPOJO;

import java.sql.Date;

public class EventEndSchedule {
	
	private int cod_fim_repeticao;
	private Date dia_fim;
	private int qtd_recorrencias;
	private int fk_evento;

	public int getCod_fim_repeticao() {
		return cod_fim_repeticao;
	}

	public void setCod_fim_repeticao(int cod_fim_repeticao) {
		this.cod_fim_repeticao = cod_fim_repeticao;
	}

	public Date getDia_fim() {
		return dia_fim;
	}

	public void setDia_fim(Date dia_fim) {
		this.dia_fim = dia_fim;
	}

	public int getQtd_recorrencias() {
		return qtd_recorrencias;
	}

	public void setQtd_recorrencias(int qtd_recorrencias) {
		this.qtd_recorrencias = qtd_recorrencias;
	}

	public int getFk_evento() {
		return fk_evento;
	}

	public void setFk_evento(int fk_evento) {
		this.fk_evento = fk_evento;
	}

}
