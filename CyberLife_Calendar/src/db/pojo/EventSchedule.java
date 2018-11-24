package db.pojo;

import java.util.Arrays;

public class EventSchedule {

	private int cod_repeticao;
	private int intervalo;
	private boolean[] dias_semana;
	private int fk_evento;
	
	public int getCod_repeticao() {
		return cod_repeticao;
	}
	public void setCod_repeticao(int cod_repeticao) {
		this.cod_repeticao = cod_repeticao;
	}
	public int getIntervalo() {
		return intervalo;
	}
	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
	public boolean[] getDias_semana() {
		return dias_semana;
	}
	public void setDias_semana(boolean[] dias_semana) {
		this.dias_semana = dias_semana;
	}
	public String getDias_semanaToString() {
		
		String list = "";
		
		for(int i = 0; i < this.dias_semana.length; i++) {
			
			list += (dias_semana[i] ? 1 : 0) + (i + 1 < dias_semana.length ? ",":"");
		}
		
		return list;
	}
	public void setDias_semanaToIntArray(String dias_semana) {
		
		for (int i = 0; i < this.dias_semana.length; i++) {
			this.dias_semana[i] = Boolean.parseBoolean(dias_semana.split(",")[i]);
		}
	}
	public int getFk_evento() {
		return fk_evento;
	}
	public void setFk_evento(int fk_evento) {
		this.fk_evento = fk_evento;
	}
}
