package db.pojo;

import java.sql.Date;

public class ReminderBanco {

	/* Dados do lembrete */
	private int cod_lembrete;
	private String titulo;
	private boolean dia_todo;
	private String status;
	private boolean recorrencia_minutos;
	private String recorrencia_tipo;
	
	/* Horarios*/
	private Date hora_inicio;
	private Date hora_fim;
	private Date data_repeticao;
	private int intervalo_minutos;
	private int dia_semana;
	private int qtd_lembrete;
	
	public int getCod_lembrete() {
		return cod_lembrete;
	}
	public void setCod_lembrete(int cod_lembrete) {
		this.cod_lembrete = cod_lembrete;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public boolean isDia_todo() {
		return dia_todo;
	}
	public void setDia_todo(boolean dia_todo) {
		this.dia_todo = dia_todo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRecorrencia_tipo() {
		return recorrencia_tipo;
	}
	public void setRecorrencia_tipo(String recorrencia_tipo) {
		this.recorrencia_tipo = recorrencia_tipo;
	}
	public Date getHora_inicio() {
		return hora_inicio;
	}
	public void setHora_inicio(Date hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	public Date getHora_fim() {
		return hora_fim;
	}
	public void setHora_fim(Date hora_fim) {
		this.hora_fim = hora_fim;
	}
	public Date getData_repeticao() {
		return data_repeticao;
	}
	public void setData_repeticao(Date data_repeticao) {
		this.data_repeticao = data_repeticao;
	}
	public int getIntervalo_minutos() {
		return intervalo_minutos;
	}
	public void setIntervalo_minutos(int intervalo_minutos) {
		this.intervalo_minutos = intervalo_minutos;
	}
	public int getDia_semana() {
		return dia_semana;
	}
	public void setDia_semana(int dia_semana) {
		this.dia_semana = dia_semana;
	}
	public int getQtd_lembrete() {
		return qtd_lembrete;
	}
	public void setQtd_lembrete(int qtd_lembrete) {
		this.qtd_lembrete = qtd_lembrete;
	}
	public boolean isRecorrencia_minutos() {
		return recorrencia_minutos;
	}
	public void setRecorrencia_minutos(boolean recorrencia_minutos) {
		this.recorrencia_minutos = recorrencia_minutos;
	}
}
