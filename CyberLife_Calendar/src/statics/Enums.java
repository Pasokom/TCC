package statics;

public class Enums {

	public static enum ReminderStatus {

		ENABLED("ATIVADO"), DISABLED("DESATIVADO"), DELETED("DELETADO");
		
		private String value;
		public String getValue() {
			return this.value;
		}
		private ReminderStatus(String value) {
			this.value = value;
		}
	}
	public static enum Day {
		SUNDAY("DOMINGO"), MONDAY("SEGUNDA"), TUESDAY("TERÇA"), WEDNESDAY("QUARTA"), THURSDAY("QUINTA"), FRIDAY("SEXTA"), SATURDAY("Sab");

		private String day;

		public String get_day() {
			return this.day;
		}

		private Day(String day) {
			this.day = day;
		}

	}

}
