package db.pojo.reminderPOJO;

public class ReminderSchedule {

	private int cod_repeticao;
	private int intervalo;
	private boolean[] dias_semana;
	private int fk_lembrete;

	/**
	 * @return the cod_repeticao
	 */
	public int getCod_repeticao() {
		return cod_repeticao;
	}

	/**
	 * @param cod_repeticao the cod_repeticao to set
	 */
	public void setCod_repeticao(int cod_repeticao) {
		this.cod_repeticao = cod_repeticao;
	}

	/**
	 * @return the intervalo
	 */
	public int getIntervalo() {
		return intervalo;
	}

	/**
	 * @param intervalo the intervalo to set
	 */
	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}

	/**
	 * @return the dias_semana
	 */
	public boolean[] getDias_semana() {
		return dias_semana;
	}

	/**
	 * @param dias_semana the dias_semana to set
	 */
	public void setDias_semana(boolean[] dias_semana) {
		this.dias_semana = dias_semana;
	}

	public String getDias_semanaToString() {

		String list = "";

		for (int i = 0; i < this.dias_semana.length; i++) {

			list += (dias_semana[i] ? 1 : 0) + (i + 1 < dias_semana.length ? "," : "");
		}

		return list;
	}

	public void setDias_semanaToArray(String dias_semana) {

		this.dias_semana = new boolean[7];

		for (int i = 0; i < this.dias_semana.length; i++) {
			this.dias_semana[i] = dias_semana.split(",")[i].equals("1") ? true : false;
		}
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