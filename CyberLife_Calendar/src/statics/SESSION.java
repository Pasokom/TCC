package statics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import db.pojo.ReminderDB;

public class SESSION {

	private static long user_cod;
	private static String user_name;
	private static String user_last_name;
	private static String user_email;
	
	private static ArrayList<ReminderDB> list_user_reminders;

	public static void set_user_cod(int cod) {
		SESSION.user_cod = cod;
	}
	public static void start_session(int cod, String email, String name, String last_name)
			throws ClassNotFoundException, SQLException {
		SESSION.user_cod = cod;
		SESSION.user_name = name;
		SESSION.user_last_name = last_name;
		SESSION.user_email = email;
	}
	public static void load_reminders () throws ClassNotFoundException, SQLException { 
		
		String sql = "SELECT * FROM VIEW_LEMBRETES WHERE L_STATUS = 'ATIVADO' AND FK_USUARIO =  " + SESSION.get_user_cod() + "';";
			
		ResultSet result = Database.get_connection().createStatement().executeQuery(sql);
		
		System.out.println(!result.first() ? "[INFORMATION] the user has no reminders" : "[CONFIRMATION] user reminders go now be loaded");
		
		while(result.next()) { 
			ReminderDB r = new ReminderDB();

			r.setReminder(result.getString("LEMBRETE"));
			r.setAll_day(result.getBoolean("LDIA_TODO"));	
			
			
			
			SESSION.user_reminders().add(r);
		}
	}
	public static ArrayList<ReminderDB> user_reminders() { 
		return SESSION.list_user_reminders == null ? new ArrayList<ReminderDB>() : list_user_reminders ;
	}
	public static long get_user_cod() {
		return user_cod;
	}

	public static String get_user_name() {
		return user_name;
	}

	public static String get_user_last_name() {
		return user_last_name;
	}

	public static String get_user_email() {
		return user_email;
	}
}
