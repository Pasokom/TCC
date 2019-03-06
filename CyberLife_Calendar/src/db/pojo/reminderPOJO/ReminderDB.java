package db.pojo.reminderPOJO;

import java.sql.Timestamp;
import db.pojo.AppointmentDB;

public class ReminderDB extends AppointmentDB {

	private int cod_lembrete;
	private String titulo;
	private Timestamp horario;
	private Timestamp horario_fim;
	private int intervalo_minutos;
	private boolean dia_todo;
	private int tipo_repeticao;
	private int tipo_fim_repeticao;
	private boolean ativo;
	private int fk_usuario;

	private ReminderSchedule schedule;
	private ReminderEndSchedule reminderEndSchedule;

	public ReminderDB() {
		super("reminder");
	}

	/**
	 * @return the cod_lembrete
	 */
	public int getCod_lembrete() {
		return cod_lembrete;
	}

	/**
	 * @param cod_lembrete the cod_lembrete to set
	 */
	public void setCod_lembrete(int cod_lembrete) {
		this.cod_lembrete = cod_lembrete;
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
	 * @return the horario
	 */
	public Timestamp getHorario() {
		return horario;
	}

	/**
	 * @param horario the horario to set
	 */
	public void setHorario(Timestamp horario) {
		this.horario = horario;
	}

	/**
	 * @return the horario_fim
	 */
	public Timestamp getHorario_fim() {
		return horario_fim;
	}

	/**
	 * @param horario_fim the horario_fim to set
	 */
	public void setHorario_fim(Timestamp horario_fim) {
		this.horario_fim = horario_fim;
	}

	/**
	 * @return the intervalo_minutos
	 */
	public int getIntervalo_minutos() {
		return intervalo_minutos;
	}

	/**
	 * @param intervalo_minutos the intervalo_minutos to set
	 */
	public void setIntervalo_minutos(int intervalo_minutos) {
		this.intervalo_minutos = intervalo_minutos;
	}

	/**
	 * @return the dia_todo
	 */
	public boolean isDia_todo() {
		return dia_todo;
	}

	/**
	 * @param dia_todo the dia_todo to set
	 */
	public void setDia_todo(boolean dia_todo) {
		this.dia_todo = dia_todo;
	}

	/**
	 * @return the tipo_repeticao
	 */
	public int getTipo_repeticao() {
		return tipo_repeticao;
	}

	/**
	 * @param tipo_repeticao the tipo_repeticao to set
	 */
	public void setTipo_repeticao(int tipo_repeticao) {
		this.tipo_repeticao = tipo_repeticao;
	}

	/**
	 * @return the tipo_fim_repeticao
	 */
	public int getTipo_fim_repeticao() {
		return tipo_fim_repeticao;
	}

	/**
	 * @param tipo_fim_repeticao the tipo_fim_repeticao to set
	 */
	public void setTipo_fim_repeticao(int tipo_fim_repeticao) {
		this.tipo_fim_repeticao = tipo_fim_repeticao;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
	 * @return the schedule
	 */
	public ReminderSchedule getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(ReminderSchedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the reminderEndSchedule
	 */
	public ReminderEndSchedule getReminderEndSchedule() {
		return reminderEndSchedule;
	}

	/**
	 * @param reminderEndSchedule the reminderEndSchedule to set
	 */
	public void setReminderEndSchedule(ReminderEndSchedule reminderEndSchedule) {
		this.reminderEndSchedule = reminderEndSchedule;
	}
}
