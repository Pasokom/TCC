package statics;

public class Enums {

	public static enum ReminderStatus { 
		
		ENABLED ("ATIVADO"), DISABLED ("DESATIVADO"), DELETED("DELETADO");
		
		private String value;
		
		public String getValue() { 
			return this.value;
		}
		private ReminderStatus(String value) { 
			this.value = value;
		}
	}
}
