package statics;

public class Enums {

	public static enum ReminderStatus {

		ENABLED("ATIVADO"), DISABLED("DESATIVADO"), DELETED("DELETADO");
		private String value;

		public String get_value() {
			return this.value;
		}

		private ReminderStatus(String value) {
			this.value = value;
		}
	}
	public static enum TypeRecurrence {

		DAYLY("DIARIA"), WEEKLY("SEMANAL"), MONTHLY("MENSAL"), YEARLY("ANUAL");

		private String value;

		public String get_value() {
			return this.value;
		}

		private TypeRecurrence(String value) {
			this.value = value;
		}

	}

}
