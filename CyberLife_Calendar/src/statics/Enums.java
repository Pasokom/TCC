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
						// tem que achar uma maneira de tratar as datas corretas
		DAYLY(1), WEEKLY(7), MONTHLY(30), YEARLY(365);

		private int  value;

		public int get_value() {
			return this.value;
		}
		
		private TypeRecurrence(int value) {
			this.value = value;
		}
	}
}










