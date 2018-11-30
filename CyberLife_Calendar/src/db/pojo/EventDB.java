package db.pojo;

import java.sql.Timestamp;

public class EventDB {
	
	private int cod_evento;
	private String titulo;
	private Timestamp data_inicio;
	private Timestamp data_fim;
	private String local_evento;
	private String descricao;
	private int tipo_repeticao;
	private int tipo_fim_repeticao;
	private int fk_usuario;
	private EventSchedule horario_evento;
	private EventEndSchedule horario_fim_evento;
	
	public int getCod_evento() {
		return cod_evento;
	}
	public void setCod_evento(int cod_evento) {
		this.cod_evento = cod_evento;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Timestamp getData_inicio() {
		return data_inicio;
	}
	public void setData_inicio(Timestamp data_inicio) {
		this.data_inicio = data_inicio;
	}
	public Timestamp getData_fim() {
		return data_fim;
	}
	public void setData_fim(Timestamp data_fim) {
		this.data_fim = data_fim;
	}
	public String getLocal_evento() {
		return local_evento;
	}
	public void setLocal_evento(String local_evento) {
		this.local_evento = local_evento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getTipo_repeticao() {
		return tipo_repeticao;
	}
	public void setTipo_repeticao(int tipo_repeticao) {
		this.tipo_repeticao = tipo_repeticao;
	}
	public int getTipo_fim_repeticao() {
		return tipo_fim_repeticao;
	}
	public void setTipo_fim_repeticao(int tipo_fim_repeticao) {
		this.tipo_fim_repeticao = tipo_fim_repeticao;
	}
	public int getFk_usuario() {
		return fk_usuario;
	}
	public void setFk_usuario(int fk_usuario) {
		this.fk_usuario = fk_usuario;
	}
	public EventSchedule getHorario_evento() {
		return horario_evento;
	}
	public void setHorario_evento(EventSchedule horario_evento) {
		this.horario_evento = horario_evento;
	}
	public EventEndSchedule getHorario_fim_evento() {
		return horario_fim_evento;
	}
	public void setHorario_fim_evento(EventEndSchedule horario_fim_evento) {
		this.horario_fim_evento = horario_fim_evento;
	}
}
