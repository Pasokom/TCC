package statics;

public class Enums {

	/**
	 * <h2>
	 * <p>
	 * Retorna valores booleanos para
	 * </p>
	 * <p>
	 * o status dos lembretes
	 * </p>
	 * </h2>
	 * 
	 * @return true para Lembrete Ativo
	 * @return false para Lembrete Desativado
	 * 
	 */
	public static enum ReminderStatus {

		ACTIVE(true), DISABLED(false);
		private boolean value;

		public boolean get_value() {
			return this.value;
		}

		private ReminderStatus(boolean value) {
			this.value = value;
		}
	}

	/**
	 * <h2>
	 * <p>
	 * Enum que retornar valores
	 * </p>
	 * <p>
	 * para os tipos de recorrencia </br>
	 * <p>
	 * </h2>
	 * @example int month = TypeRecurrence.MONTHLY.getValue();
	 */
	public static enum TypeRecurrence {
		// tem que achar uma maneira de tratar as datas corretas
		DAYLY(1), WEEKLY(2), MONTHLY(3), YEARLY(4);

		private int value;

		public int getValue() {
			return this.value;
		}

		private TypeRecurrence(int value) {
			this.value = value;
		}
	}

	/**
	 * <p>
	 * <h2>Usado para definir o tipo de repetição do lembrete (repetição de horario)
	 * </h2>
	 * </p>
	 * 
	 * @return 0 - repete o dia todo
	 * @return 1 - repetição por intervalo
	 * @return 2 - repetição pelo time picker
	 */
	public static enum RepetitionType {

		ALL_DAY(0), INTERVAL(1), TIME_PICKER(2);

		private int value;

		public int getValue() {
			return this.value;
		}

		private RepetitionType(int value) {
			this.value = value;
		}
	}

	public static enum DayOfWeek {
		SUNDAY("Dom"), MONDAY("Seg"), TUESDAY("Ter"), WEDNESDAY("Qua"), THURSDAY("Qui"), FRYDAY("Sex"), SATURDAY("Sab");

		String name;

		DayOfWeek(String name) {
			this.name = name;
		}

		public String getValue() {
			return this.name;
		}
	}

	public static enum Month {

		JANUARY("Janeiro"), FEBRUARY("Fevereiro"), MARCH("Mar�o"), APRIL("Abril"), MAY("Maio"), JUNE("Junho"),
		JULY("Julho"), AUGUST("Agosto"), SEPTEMBER("Setembro"), OCTOBER("Outubro"), NOVEMBER("Novembro"),
		DECEMBER("Dezembro");

		String name;

		Month(String name) {
			this.name = name;
		}

		public String getValue() {
			return this.name;
		}
	}
}
